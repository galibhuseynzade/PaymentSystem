package paymentsystem.exception.exceptions;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() {
        super("Card not found");
    }
}
