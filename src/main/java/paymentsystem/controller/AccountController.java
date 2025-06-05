package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @GetMapping
    public Page<AccountDto> getAllAccounts(@RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return accountService.getAllAccounts(pageable);
    }

    @GetMapping("/getAllActiveAccounts")
    public Page<AccountDto> getAllActiveAccounts(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                 @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return accountService.getAllActiveAccounts(pageable);
    }

    @GetMapping("/getAccountsByCustomerId/{customerId}")
    public Page<AccountDto> getAccountsByCustomerId(@PathVariable Integer customerId,
                                                    @RequestParam(defaultValue = "0", required = false) Integer page,
                                                    @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return accountService.getAccountsByCustomerId(customerId, pageable);
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
