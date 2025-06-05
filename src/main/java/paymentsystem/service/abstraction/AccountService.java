package paymentsystem.service.abstraction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import paymentsystem.model.dto.AccountDto;

import java.math.BigDecimal;

public interface AccountService {
    AccountDto createAccount(Integer customerId);
    void activateAccount(String accountNumber);
    void depositAccount(String accountNumber, BigDecimal amount);
    Page<AccountDto> getAccountsByCustomerId(Integer customerId, Pageable pageable);
    Page<AccountDto> getAllActiveAccounts(Pageable pageable);
    Page<AccountDto> getAllAccounts(Pageable pageable);
}
