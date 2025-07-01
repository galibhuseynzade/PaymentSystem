package paymentsystem.service.concrete;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import paymentsystem.config.LimitProperties;
import paymentsystem.exception.exceptions.AccountNotFoundException;
import paymentsystem.exception.exceptions.CustomerNotFoundException;
import paymentsystem.exception.exceptions.InactiveAccountDepositException;
import paymentsystem.exception.exceptions.InvalidAccountActivationException;
import paymentsystem.exception.exceptions.MaximumAccountCountException;
import paymentsystem.mapper.AccountMapper;
import paymentsystem.model.dto.AccountDto;
import paymentsystem.model.entity.AccountEntity;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.enums.AccountStatus;
import paymentsystem.repository.AccountRepository;
import paymentsystem.repository.CustomerRepository;
import paymentsystem.service.abstraction.AccountService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    CustomerRepository customerRepository;
    List<AccountStatus> validAccountStatusList = Arrays.asList(AccountStatus.NEW, AccountStatus.ACTIVE);
    LimitProperties limitProperties;

    @Override
    public AccountDto createAccount(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        if (accountRepository.countByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList) >= limitProperties.getMaxAccountCount())
            throw new MaximumAccountCountException(limitProperties.getMaxAccountCount());

        AccountEntity accountEntity = accountMapper.buildAccountEntity(customerEntity);

        accountRepository.save(accountEntity);
        log.info("Account created");
        return accountMapper.getAccountDto(accountEntity);
    }

    @Override
    public Boolean activateAccount(String accountNumber) {
        AccountEntity accountEntity = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);

        if (!accountEntity.getStatus().equals(AccountStatus.NEW))
            throw new InvalidAccountActivationException();

        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(accountEntity);
        log.info("Account activated " + accountNumber);
        return true;
    }

    @Override
    public Boolean depositAccount(String accountNumber, BigDecimal amount) {
        AccountEntity accountEntity = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);
        if (!accountEntity.getStatus().equals(AccountStatus.ACTIVE))
            throw new InactiveAccountDepositException();

        BigDecimal newBalance = accountEntity.getBalance().add(amount);
        accountEntity.setBalance(newBalance);
        accountRepository.save(accountEntity);
        return true;
    }

    @Override
    public List<AccountDto> getAccountsByCustomerId(Integer customerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountEntity> accountEntityPage = accountRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList, pageable);
        List<AccountDto> accountDtoList = accountMapper.getAccountDtoList(accountEntityPage.getContent());
        return new PageImpl<>(accountDtoList, accountEntityPage.getPageable(), accountEntityPage.getTotalElements()).getContent();
    }

    @Override
    public List<AccountDto> getAllActiveAccounts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountEntity> accountEntityPage = accountRepository.findByStatus(AccountStatus.ACTIVE, pageable);
        List<AccountDto> accountDtoList = accountMapper.getAccountDtoList(accountEntityPage.getContent());
        return new PageImpl<>(accountDtoList, pageable, accountEntityPage.getTotalElements()).getContent();
    }

    @Override
    public List<AccountDto> getAllAccounts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AccountEntity> accountEntityPage = accountRepository.findAll(pageable);
        List<AccountDto> accountDtoList = accountMapper.getAccountDtoList(accountEntityPage.getContent());
        return new PageImpl<>(accountDtoList, pageable, accountEntityPage.getTotalElements()).getContent();
    }
}
