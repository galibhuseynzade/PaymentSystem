package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<AccountDto> findAllAccounts() {
        return accountService.findAllAccounts();
    }

    @GetMapping("/findAllActiveAccounts")
    public List<AccountDto> findAllActiveAccounts() {
        return accountService.findAllActiveAccounts();
    }

    @GetMapping("/findAccountsByCustomerId")
    public List<AccountDto> findAccountsByCustomerId(@RequestParam Integer customerId) {
        return accountService.findAccountsByCustomerId(customerId);
    }

    @PostMapping("/createAccount")
    public AccountDto createAccount(@RequestParam Integer customerId) {
        return accountService.createAccount(customerId);
    }

    @PutMapping("/activateAccount")
    public String activateAccount(@RequestParam String accountNumber) {
        return accountService.activateAccount(accountNumber);
    }

    @PutMapping("/depositAccount")
    public String depositAccount(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        return accountService.depositAccount(accountNumber, amount);
    }
}
