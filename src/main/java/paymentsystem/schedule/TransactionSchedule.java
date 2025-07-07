package paymentsystem.schedule;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import paymentsystem.config.DefaultProperties;
import paymentsystem.config.LimitProperties;
import paymentsystem.exception.exceptions.AccountNotFoundException;
import paymentsystem.exception.exceptions.CardNotFoundException;
import paymentsystem.model.entity.AccountEntity;
import paymentsystem.model.entity.CardEntity;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.entity.TransactionEntity;
import paymentsystem.model.enums.CustomerStatus;
import paymentsystem.model.enums.TransactionStatus;
import paymentsystem.repository.AccountRepository;
import paymentsystem.repository.CardRepository;
import paymentsystem.repository.CustomerRepository;
import paymentsystem.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionSchedule {
    TransactionRepository transactionRepository;
    AccountRepository accountRepository;
    CardRepository cardRepository;
    CustomerRepository customerRepository;
    LimitProperties limitProperties;
    DefaultProperties defaultProperties;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void generateTransactions() {
        List<TransactionEntity> transactionEntityList = transactionRepository.findByStatus(TransactionStatus.PENDING);
        for (TransactionEntity transactionEntity : transactionEntityList) {
            checkCustomerStatus(transactionEntity.getCustomerEntity());
            updateDebitBalance(transactionEntity.getDebit(), transactionEntity.getAmount());
            updateCreditBalance(transactionEntity.getCredit(), transactionEntity.getAmount());

            transactionEntity.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transactionEntity);
            log.info("Transaction updated");
        }
    }

    private void checkCustomerStatus(CustomerEntity customerEntity) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(defaultProperties.getDefaultMonthlyPeriod());
        if (transactionRepository.getMonthlyTotalByCustomer(customerEntity.getCustomerId(), startDate, endDate).compareTo(limitProperties.getDailyTransactionLimit()) > 0) {
            customerEntity.setStatus(CustomerStatus.SUSPECTED);
            customerRepository.save(customerEntity);
        }
    }

    private void updateDebitBalance(String debit, BigDecimal amount) {
        if (debit.length() == defaultProperties.getDefaultAccountLength()) {
            AccountEntity accountEntity = accountRepository.findById(debit).orElseThrow(AccountNotFoundException::new);
            accountEntity.setBalance(accountEntity.getBalance().subtract(amount));
            accountRepository.save(accountEntity);
        } else {
            CardEntity cardEntity = cardRepository.findById(debit).orElseThrow(CardNotFoundException::new);
            cardEntity.setBalance(cardEntity.getBalance().subtract(amount));
            cardRepository.save(cardEntity);
        }
    }

    private void updateCreditBalance(String credit, BigDecimal amount) {
        if (credit.length() == defaultProperties.getDefaultAccountLength()) {
            AccountEntity accountEntity = accountRepository.findById(credit).orElse(null);
            if (accountEntity != null) {
                accountEntity.setBalance(accountEntity.getBalance().add(amount));
                accountRepository.save(accountEntity);
            }
        } else {
            CardEntity cardEntity = cardRepository.findById(credit).orElse(null);
            if (cardEntity != null) {
                cardEntity.setBalance(cardEntity.getBalance().add(amount));
                cardRepository.save(cardEntity);
            }
        }
    }
}
