package paymentsystem.schedule;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import paymentsystem.config.DefaultValueConfiguration;
import paymentsystem.config.LimitConfiguration;
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
public class TransactionSchedule {
    TransactionRepository transactionRepository;
    AccountRepository accountRepository;
    CardRepository cardRepository;
    CustomerRepository customerRepository;
    LimitConfiguration limitConfiguration;
    DefaultValueConfiguration defaultValueConfiguration;

    //    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(fixedRate = 30000)
    @Transactional
    public void generateTransactions() {
        try {
            List<TransactionEntity> transactionEntityList = transactionRepository.findByStatus(TransactionStatus.PENDING);
            for (TransactionEntity transactionEntity : transactionEntityList) {
                checkCustomerStatus(transactionEntity.getCustomerEntity());
                updateDebitBalance(transactionEntity.getDebit(), transactionEntity.getAmount());
                updateCreditBalance(transactionEntity.getCredit(), transactionEntity.getAmount());

                transactionEntity.setStatus(TransactionStatus.SUCCESS);
                transactionRepository.save(transactionEntity);
                log.info("Transaction updated");
            }
        } catch (NullPointerException ex) {
            log.info("No transactions found");
        }
    }

    private void updateDebitBalance(String debit, BigDecimal amount) {
        if (debit.length() == defaultValueConfiguration.getAccountLength()) {
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
        if (credit.length() == defaultValueConfiguration.getAccountLength()) {
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

    private void checkCustomerStatus(CustomerEntity customerEntity) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        if (transactionRepository.getMonthlyTotalByCustomer(customerEntity.getCustomerId(), startDate, endDate).compareTo(limitConfiguration.getDailyTransactionLimit()) > 0) {
            customerEntity.setStatus(CustomerStatus.SUSPECTED);
            customerRepository.save(customerEntity);
        }
    }
}
