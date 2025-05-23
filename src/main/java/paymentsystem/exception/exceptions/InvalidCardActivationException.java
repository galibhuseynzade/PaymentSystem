package paymentsystem.exception.exceptions;

public class InvalidCardActivationException extends RuntimeException {
    public InvalidCardActivationException() {
        super("You can only activate a new card");
    }
}
