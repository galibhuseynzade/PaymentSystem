package paymentsystem.exception.exceptions;

public class MaximumAccountCountException extends RuntimeException {
    public MaximumAccountCountException(int maxAccountCount) {
        super("Customer can have maximum " + maxAccountCount + " accounts");
    }
}
