package paymentsystem.service.abstraction;

import paymentsystem.model.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    TransactionDto transfer(String debit, String credit, BigDecimal amount);
    List<TransactionDto> getTransactionsByCustomerId(Integer customerId, Integer page, Integer size);
    List<TransactionDto> getAllTransactions(Integer page, Integer size);
}
