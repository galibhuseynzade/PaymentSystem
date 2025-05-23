package paymentsystem.exception.exceptions;

public class LimitExceedsException extends RuntimeException {
    public LimitExceedsException() {
        super("Limit exceeds for today");
    }
}
