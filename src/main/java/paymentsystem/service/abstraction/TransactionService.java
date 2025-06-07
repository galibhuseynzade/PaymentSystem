package paymentsystem.service.abstraction;

import org.springframework.data.domain.Page;
import paymentsystem.model.dto.TransactionDto;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionDto transfer(String debit, String credit, BigDecimal amount);
    Page<TransactionDto> getTransactionsByCustomerId(Integer customerId, Integer page, Integer size);
    Page<TransactionDto> getAllTransactions(Integer page, Integer size);
}
