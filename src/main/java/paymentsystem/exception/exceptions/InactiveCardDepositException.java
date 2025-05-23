package paymentsystem.exception.exceptions;

public class InactiveCardDepositException extends RuntimeException {
    public InactiveCardDepositException() {
        super("You can only deposit to active card");
    }
}
