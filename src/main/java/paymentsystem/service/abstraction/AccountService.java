package paymentsystem.service.abstraction;

import paymentsystem.model.dto.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountDto createAccount(Integer customerId);
    Boolean activateAccount(String accountNumber);
    Boolean depositAccount(String accountNumber, BigDecimal amount);
    List<AccountDto> getAccountsByCustomerId(Integer customerId, Integer page, Integer size);
    List<AccountDto> getAllActiveAccounts(Integer page, Integer size);
    List<AccountDto> getAllAccounts(Integer page, Integer size);
    AccountDto getAccountByAccountNumber(String accountNumber);
}
