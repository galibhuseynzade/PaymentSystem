package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.TransactionDto;
import paymentsystem.service.abstraction.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {
    TransactionService transactionService;

    @GetMapping
    public List<TransactionDto> getAllTransactions(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                   @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return transactionService.getAllTransactions(page, size);
    }

    @GetMapping("/transactionsByCustomerId/{customerId}")
    public List<TransactionDto> getTransactionsByCustomerId(@PathVariable Integer customerId,
                                                            @RequestParam(defaultValue = "0", required = false) Integer page,
                                                            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return transactionService.getTransactionsByCustomerId(customerId, page, size);
    }

    @PostMapping("/transfer")
    public TransactionDto transfer(@RequestParam String debit,
                                   @RequestParam String credit,
                                   @RequestParam BigDecimal amount
    ) {
        return transactionService.transfer(debit, credit, amount);
    }
}
