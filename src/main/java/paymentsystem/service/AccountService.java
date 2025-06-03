package paymentsystem.service;

import paymentsystem.model.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountDto createAccount(Integer customerId);
    void activateAccount(String accountNumber);
    void depositAccount(String accountNumber, BigDecimal amount);
    List<AccountDto> getAccountsByCustomerId(Integer customerId);
    List<AccountDto> getAllActiveAccounts();
    List<AccountDto> getAllAccounts();
}
