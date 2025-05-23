package paymentsystem.exception.exceptions;

public class InvalidAccountActivationException extends RuntimeException {
    public InvalidAccountActivationException() {
        super("You can only activate a new account");
    }
}
