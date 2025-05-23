package paymentsystem.exception.exceptions;

public class UserAlreadyDisabledException extends RuntimeException {
    public UserAlreadyDisabledException() {
        super("User is already disabled");
    }
}
