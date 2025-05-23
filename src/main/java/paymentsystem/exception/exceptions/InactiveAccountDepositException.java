package paymentsystem.exception.exceptions;

public class InactiveAccountDepositException extends RuntimeException {
    public InactiveAccountDepositException() {
        super("You can only deposit to active account");
    }
}
