package paymentsystem.service.abstraction;

import org.springframework.data.domain.Page;
import paymentsystem.model.dto.AccountDto;

import java.math.BigDecimal;

public interface AccountService {
    AccountDto createAccount(Integer customerId);
    void activateAccount(String accountNumber);
    void depositAccount(String accountNumber, BigDecimal amount);
    Page<AccountDto> getAccountsByCustomerId(Integer customerId, Integer page, Integer size);
    Page<AccountDto> getAllActiveAccounts(Integer size, Integer page);
    Page<AccountDto> getAllAccounts(Integer size, Integer page);
}
