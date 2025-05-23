package paymentsystem.exception.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Username already exists");
    }
}
