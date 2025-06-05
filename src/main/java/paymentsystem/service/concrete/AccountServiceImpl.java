package paymentsystem.service.concrete;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.ArrayList;
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
        return getAccountDto(accountEntity);
    }

    @Override
    public void activateAccount(String accountNumber) {
        AccountEntity accountEntity = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);

        if (!accountEntity.getStatus().equals(AccountStatus.NEW))
            throw new InvalidAccountActivationException();

        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(accountEntity);
        log.info("Account activated " + accountNumber);
    }

    @Override
    public void depositAccount(String accountNumber, BigDecimal amount) {
        AccountEntity accountEntity = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);
        if (!accountEntity.getStatus().equals(AccountStatus.ACTIVE))
            throw new InactiveAccountDepositException();

        BigDecimal newBalance = accountEntity.getBalance().add(amount);
        accountEntity.setBalance(newBalance);
        accountRepository.save(accountEntity);
    }

    @Override
    public Page<AccountDto> getAccountsByCustomerId(Integer customerId, Pageable pageable) {
        Page<AccountEntity> accountEntityPage = accountRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList, pageable);
        List<AccountDto> accountDtoList = getAccountDtoList(accountEntityPage.getContent());
        return new PageImpl<>(accountDtoList, accountEntityPage.getPageable(), accountEntityPage.getTotalElements());
    }

    @Override
    public Page<AccountDto> getAllActiveAccounts(Pageable pageable) {
        Page<AccountEntity> accountEntityPage = accountRepository.findByStatus(AccountStatus.ACTIVE, pageable);
        List<AccountDto> accountDtoList = getAccountDtoList(accountEntityPage.getContent());
        return new PageImpl<>(accountDtoList, pageable, accountEntityPage.getTotalElements());
    }

    @Override
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        Page<AccountEntity> accountEntityPage = accountRepository.findAll(pageable);
        List<AccountDto> accountDtoList = getAccountDtoList(accountEntityPage.getContent());
        return new PageImpl<>(accountDtoList, pageable, accountEntityPage.getTotalElements());
    }

    private List<AccountDto> getAccountDtoList(List<AccountEntity> accountEntityList) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        for (AccountEntity accountEntity : accountEntityList) {
            accountDtoList.add(getAccountDto(accountEntity));
        }
        return accountDtoList;
    }

    private AccountDto getAccountDto(AccountEntity accountEntity) {
        AccountDto accountDto = accountMapper.mapToAccountDto(accountEntity);
        accountDto.setCustomerId(accountEntity.getCustomerEntity().getCustomerId());
        return accountDto;
    }
}
