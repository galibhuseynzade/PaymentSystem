package paymentsystem.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import paymentsystem.config.LimitConfiguration;
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
    LimitConfiguration limitConfiguration;

    @Override
    public AccountDto createAccount(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        if (accountRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList).size() >= limitConfiguration.getMaxAccountCount())
            throw new MaximumAccountCountException(limitConfiguration.getMaxAccountCount());

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
    public List<AccountDto> getAccountsByCustomerId(Integer customerId) {
        List<AccountEntity> accountEntityList = accountRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList);
        return getAccountDtoList(accountEntityList);
    }

    @Override
    public List<AccountDto> getAllActiveAccounts() {
        List<AccountEntity> accountEntityList = accountRepository.findByStatus(AccountStatus.ACTIVE);
        return getAccountDtoList(accountEntityList);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<AccountEntity> accountEntityList = accountRepository.findAll();
        return getAccountDtoList(accountEntityList);
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
