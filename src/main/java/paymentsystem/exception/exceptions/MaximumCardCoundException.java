package paymentsystem.exception.exceptions;

public class MaximumCardCoundException extends RuntimeException {
    public MaximumCardCoundException(Integer maxCardCount) {
        super("Customer can have maximum " + maxCardCount + " cards");
    }
}
