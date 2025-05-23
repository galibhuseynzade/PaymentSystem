package paymentsystem.exception.exceptions;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException() {
        super("Not enough funds");
    }
}
