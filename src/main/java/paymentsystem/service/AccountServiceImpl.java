package paymentsystem.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import paymentsystem.config.DefaultValueConfiguration;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    DefaultValueConfiguration defaultValueConfiguration;

    @Override
    public AccountDto createAccount(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        if (accountRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList).size() >= limitConfiguration.getMaxAccountCount())
            throw new MaximumAccountCountException(limitConfiguration.getMaxAccountCount());

        String accountNumber = generateAccountNumber();
        AccountEntity accountEntity = AccountEntity.builder()
                .accountNumber(accountNumber)
                .customerEntity(customerEntity)
                .balance(BigDecimal.ZERO)
                .openingDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(defaultValueConfiguration.getAccountPeriodByYears()))
                .status(AccountStatus.NEW)
                .build();

        accountRepository.save(accountEntity);
        log.info("Account created " + accountNumber);
        return getAccountDto(accountEntity);
    }

    @Override
    public String activateAccount(String accountNumber) {
        AccountEntity accountEntity = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);

        if (!accountEntity.getStatus().equals(AccountStatus.NEW))
            throw new InvalidAccountActivationException();

        accountEntity.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(accountEntity);
        log.info("Account activated " + accountNumber);
        return "Account activated successfully";
    }

    @Override
    public String depositAccount(String accountNumber, BigDecimal amount) {
        AccountEntity accountEntity = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);
        if (!accountEntity.getStatus().equals(AccountStatus.ACTIVE))
            throw new InactiveAccountDepositException();

        BigDecimal newBalance = accountEntity.getBalance().add(amount);
        accountEntity.setBalance(newBalance);
        accountRepository.save(accountEntity);

        return "Balance is " + newBalance;
    }

    @Override
    public List<AccountDto> findAccountsByCustomerId(Integer customerId) {
        List<AccountEntity> accountEntityList = accountRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList);
        return getAccountDtoList(accountEntityList);
    }

    @Override
    public List<AccountDto> findAllActiveAccounts() {
        List<AccountEntity> accountEntityList = accountRepository.findByStatus(AccountStatus.ACTIVE);
        return getAccountDtoList(accountEntityList);
    }

    @Override
    public List<AccountDto> findAllAccounts() {
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

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("AZ");

        for (int i = 0; i < 18; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }
        return stringBuilder.toString();
    }
}
