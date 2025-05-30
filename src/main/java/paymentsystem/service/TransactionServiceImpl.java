package paymentsystem.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import paymentsystem.config.DefaultValueConfiguration;
import paymentsystem.config.LimitConfiguration;
import paymentsystem.exception.exceptions.AccountNotActiveException;
import paymentsystem.exception.exceptions.CardNotFoundException;
import paymentsystem.exception.exceptions.LimitExceedsException;
import paymentsystem.exception.exceptions.NotEnoughFundsException;
import paymentsystem.mapper.TransactionMapper;
import paymentsystem.model.dto.TransactionDto;
import paymentsystem.model.entity.AccountEntity;
import paymentsystem.model.entity.CardEntity;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.entity.TransactionEntity;
import paymentsystem.model.enums.AccountStatus;
import paymentsystem.model.enums.CardStatus;
import paymentsystem.model.enums.TransactionStatus;
import paymentsystem.repository.AccountRepository;
import paymentsystem.repository.CardRepository;
import paymentsystem.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionServiceImpl implements TransactionService {
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;
    AccountRepository accountRepository;
    CardRepository cardRepository;
    LimitConfiguration limitConfiguration;
    DefaultValueConfiguration defaultValueConfiguration;

    @Override
    public TransactionDto transfer(String debit, String credit, BigDecimal amount) {
        checkCreditNumber(credit);

        if (debit.length() == defaultValueConfiguration.getCardLength()) {
            return transferFromCard(debit, credit, amount);
        } else if (debit.length() == defaultValueConfiguration.getAccountLength()) {
            return transferFromAccount(debit, credit, amount);
        } else
            throw new IllegalArgumentException("Wrong input for debit");
    }

    private TransactionDto transferFromAccount(String debit, String credit, BigDecimal amount) {
        AccountEntity accountEntity = accountRepository.findById(debit).orElseThrow();

        if (!accountEntity.getStatus().equals(AccountStatus.ACTIVE))
            throw new AccountNotActiveException();

        CustomerEntity customerEntity = accountEntity.getCustomerEntity();
        checkCustomerTransactions(customerEntity);

        if (accountEntity.getBalance().compareTo(amount) < 0)
            throw new NotEnoughFundsException();

        if (accountEntity.getBalance().compareTo(limitConfiguration.getMinAcceptableAccountBalance()) < 0)
            throw new LimitExceedsException();

        return createTransaction(customerEntity, debit, credit, amount);
    }

    private TransactionDto transferFromCard(String debit, String credit, BigDecimal amount) {
        CardEntity cardEntity = cardRepository.findById(debit).orElseThrow(CardNotFoundException::new);

        CustomerEntity customerEntity = cardEntity.getCustomerEntity();
        checkCustomerTransactions(customerEntity);

        if (cardEntity.getBalance().compareTo(limitConfiguration.getMinAcceptableCardBalance()) >= 0
                && cardEntity.getBalance().compareTo(amount) >= 0
                && cardEntity.getStatus().equals(CardStatus.ACTIVE)) {
            return createTransaction(customerEntity, debit, credit, amount);
        } else {
            return transferFromAccountWhenCardFailed(customerEntity, credit, amount);
        }
    }

    private TransactionDto transferFromAccountWhenCardFailed(CustomerEntity customerEntity, String credit, BigDecimal amount) {
        Integer customerId = customerEntity.getCustomerId();
        List<AccountEntity> accountEntityList = accountRepository.findByCustomerEntity_CustomerIdAndStatus(customerId, AccountStatus.ACTIVE);
        if (accountEntityList.isEmpty())
            throw new NotEnoughFundsException();

        AccountEntity accountEntityWithMaxBalance = accountEntityList.getFirst();
        for (AccountEntity accountEntity : accountEntityList) {
            if (accountEntity.getBalance().compareTo(accountEntityWithMaxBalance.getBalance()) > 0)
                accountEntityWithMaxBalance = accountEntity;
        }
        if (accountEntityWithMaxBalance.getBalance().compareTo(amount) < 0)
            throw new NotEnoughFundsException();

        return createTransaction(customerEntity, accountEntityWithMaxBalance.getAccountNumber(), credit, amount);
    }

    private void checkCustomerTransactions(CustomerEntity customerEntity) {
        if (!transactionRepository.findByCustomerEntityAndDate(customerEntity, LocalDate.now()).isEmpty())
            throw new LimitExceedsException();
    }

    private void checkCreditNumber(String credit) {
        if (credit.length() != defaultValueConfiguration.getAccountLength() && credit.length() != defaultValueConfiguration.getCardLength())
            throw new IllegalArgumentException("Wrong input for credit");
    }

    private TransactionDto createTransaction(CustomerEntity customerEntity,
                                             String debit, String credit,
                                             BigDecimal amount) {
        TransactionEntity transactionEntity = TransactionEntity.builder()
                .transactionId(generateTransactionId())
                .customerEntity(customerEntity)
                .debit(debit)
                .credit(credit)
                .date(LocalDate.now())
                .amount(amount)
                .status(TransactionStatus.PENDING)
                .build();

        transactionRepository.save(transactionEntity);
        return getTransactionDto(transactionEntity);
    }

    @Override
    public List<TransactionDto> findTransactionsByCustomerId(Integer customerId) {
        List<TransactionEntity> transactionEntities = transactionRepository.findByCustomerEntity_CustomerId(customerId);
        return getTransactionDtoList(transactionEntities);
    }

    @Override
    public List<TransactionDto> findAllTransactions() {
        List<TransactionEntity> transactionEntities = transactionRepository.findAll();
        return getTransactionDtoList(transactionEntities);
    }

    private TransactionDto getTransactionDto(TransactionEntity transactionEntity) {
        TransactionDto transactionDto = transactionMapper.mapToTransactionDto(transactionEntity);
        transactionDto.setCustomerId(transactionEntity.getCustomerEntity().getCustomerId());
        return transactionDto;
    }

    private List<TransactionDto> getTransactionDtoList(List<TransactionEntity> transactionEntities) {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (TransactionEntity transactionEntity : transactionEntities) {
            transactionDtoList.add(getTransactionDto(transactionEntity));
        }
        return transactionDtoList;
    }

    private String generateTransactionId() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("TR");
        for (int i = 0; i < 18; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
