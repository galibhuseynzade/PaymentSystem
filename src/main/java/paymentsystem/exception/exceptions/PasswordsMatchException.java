package paymentsystem.exception.exceptions;

public class PasswordsMatchException extends RuntimeException {

    public PasswordsMatchException() {
        super("Passwords match");
    }
}
