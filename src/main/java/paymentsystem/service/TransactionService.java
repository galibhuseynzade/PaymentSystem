package paymentsystem.service;

import paymentsystem.model.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionDto transfer(String debit, String credit, BigDecimal amount);
    List<TransactionDto> findTransactionsByCustomerId(Integer customerId);
    List<TransactionDto> findAllTransactions();
}
