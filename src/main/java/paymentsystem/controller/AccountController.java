package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.AccountDto;
import paymentsystem.service.abstraction.AccountService;

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
        return accountService.getAllAccounts(page, size);
    }

    @GetMapping("/allActiveAccounts")
    public Page<AccountDto> getAllActiveAccounts(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                 @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return accountService.getAllActiveAccounts(page, size);
    }

    @GetMapping("/accountsByCustomerId/{customerId}")
    public Page<AccountDto> getAccountsByCustomerId(@PathVariable Integer customerId,
                                                    @RequestParam(defaultValue = "0", required = false) Integer page,
                                                    @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return accountService.getAccountsByCustomerId(customerId, page, size);
    }

    @PostMapping("/{customerId}")
    public AccountDto createAccount(@PathVariable Integer customerId) {
        return accountService.createAccount(customerId);
    }

    @PutMapping("/activateAccount/{accountNumber}")
    public Boolean activateAccount(@PathVariable String accountNumber) {
        return accountService.activateAccount(accountNumber);
    }

    @PutMapping("/depositAccount/{accountNumber}")
    public Boolean depositAccount(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return accountService.depositAccount(accountNumber, amount);
    }
}
