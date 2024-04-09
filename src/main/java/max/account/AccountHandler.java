package max.account;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import max.account.exceptions.AccountAlreadyExistsdException;
import max.account.exceptions.AccountNotFoundException;
import max.account.exceptions.ExceptionOut;
import max.account.exceptions.InternalHashingError;
import max.account.exceptions.LoginFailedException;

@ControllerAdvice
public class AccountHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionOut> handleAccountNotFoundException(AccountNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionOut(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ExceptionOut> handleLoginFailedException(LoginFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionOut(HttpStatus.UNAUTHORIZED, e.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(InternalHashingError.class)
    public ResponseEntity<ExceptionOut> handleInternalHashingError(InternalHashingError e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionOut(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), LocalDateTime.now().toString()));
    }

    @ExceptionHandler(AccountAlreadyExistsdException.class)
    public ResponseEntity<ExceptionOut> handleAccountAlreadyExistsdException(AccountAlreadyExistsdException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionOut(HttpStatus.CONFLICT, e.getMessage(), LocalDateTime.now().toString()));
    }
    
}
