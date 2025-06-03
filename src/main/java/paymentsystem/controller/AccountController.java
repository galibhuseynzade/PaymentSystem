package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.AccountDto;
import paymentsystem.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/getAllActiveAccounts")
    public List<AccountDto> getAllActiveAccounts() {
        return accountService.getAllActiveAccounts();
    }

    @GetMapping("/getAccountsByCustomerId/{customerId}")
    public List<AccountDto> getAccountsByCustomerId(@PathVariable Integer customerId) {
        return accountService.getAccountsByCustomerId(customerId);
    }

    @PostMapping("/createAccount")
    public AccountDto createAccount(@RequestParam Integer customerId) {
        return accountService.createAccount(customerId);
    }

    @PutMapping("/activateAccount")
    public void activateAccount(@RequestParam String accountNumber) {
        accountService.activateAccount(accountNumber);
    }

    @PutMapping("/depositAccount")
    public void depositAccount(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        accountService.depositAccount(accountNumber, amount);
    }
}
