package paymentsystem.service;

import paymentsystem.model.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountDto createAccount(Integer customerId);
    String activateAccount(String accountNumber);
    String depositAccount(String accountNumber, BigDecimal amount);
    List<AccountDto> findAccountsByCustomerId(Integer customerId);
    List<AccountDto> findAllActiveAccounts();
    List<AccountDto> findAllAccounts();
}
