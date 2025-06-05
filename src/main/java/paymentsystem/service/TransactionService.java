package paymentsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import paymentsystem.model.dto.TransactionDto;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionDto transfer(String debit, String credit, BigDecimal amount);
    Page<TransactionDto> getTransactionsByCustomerId(Integer customerId, Pageable pageable);
    Page<TransactionDto> getAllTransactions(Pageable pageable);
}
