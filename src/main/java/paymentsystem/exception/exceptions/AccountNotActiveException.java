package paymentsystem.exception.exceptions;

public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException() {
        super("Account not active");
    }
}
