package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.TransactionDto;
import paymentsystem.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {
    TransactionService transactionService;

    @GetMapping
    public List<TransactionDto> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping("/findTransactionsByCustomerId")
    public List<TransactionDto> findTransactionsByCustomerId(@RequestParam Integer customerId) {
        return transactionService.findTransactionsByCustomerId(customerId);
    }

    @PostMapping("/transfer")
    public TransactionDto transfer(@RequestParam String debit, @RequestParam String credit, @RequestParam BigDecimal amount) {
        return transactionService.transfer(debit, credit, amount);
    }
}
