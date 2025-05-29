package paymentsystem.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import paymentsystem.exception.exceptions.AccountNotActiveException;
import paymentsystem.exception.exceptions.AccountNotFoundException;
import paymentsystem.exception.exceptions.CardNotFoundException;
import paymentsystem.exception.exceptions.CustomerNotFoundException;
import paymentsystem.exception.exceptions.InactiveAccountDepositException;
import paymentsystem.exception.exceptions.InactiveCardDepositException;
import paymentsystem.exception.exceptions.IncorrectPasswordException;
import paymentsystem.exception.exceptions.InvalidAccountActivationException;
import paymentsystem.exception.exceptions.InvalidCardActivationException;
import paymentsystem.exception.exceptions.MaximumAccountCountException;
import paymentsystem.exception.exceptions.MaximumCardCoundException;
import paymentsystem.exception.exceptions.NotEnoughFundsException;
import paymentsystem.exception.exceptions.PasswordsMatchException;
import paymentsystem.exception.exceptions.UserAlreadyActiveException;
import paymentsystem.exception.exceptions.UserAlreadyDisabledException;
import paymentsystem.exception.exceptions.UserAlreadyExistsException;
import paymentsystem.exception.exceptions.UserNotFoundException;
import paymentsystem.exception.model.ResponseModel;

import javax.naming.LimitExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ResponseModel> buildResponse(String message, HttpStatus status) {
        ResponseModel responseModel = new ResponseModel(message, status.value());
        return new ResponseEntity<>(responseModel, status);
    }

    @ExceptionHandler({
            AccountNotActiveException.class,
//            IllegalArgumentException.class,
            InactiveAccountDepositException.class,
            InactiveCardDepositException.class,
            InvalidAccountActivationException.class,
            InvalidCardActivationException.class,
            LimitExceededException.class,
            MaximumAccountCountException.class,
            MaximumCardCoundException.class,
            NotEnoughFundsException.class,
            PasswordsMatchException.class,
            UserAlreadyActiveException.class,
            UserAlreadyDisabledException.class,
            UserAlreadyExistsException.class
    })
    public ResponseEntity<ResponseModel> handleBadRequest(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            AccountNotFoundException.class,
            CardNotFoundException.class,
            CustomerNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<ResponseModel> handleNotFound(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ResponseModel> handleUnauthorized(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}



