package paymentsystem.exception.exceptions;

public class UserHasBeenDisabledException extends RuntimeException {
    public UserHasBeenDisabledException() {
        super("User has been disabled");
    }
}
