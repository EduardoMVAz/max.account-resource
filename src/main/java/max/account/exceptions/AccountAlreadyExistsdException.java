package max.account.exceptions;

public class AccountAlreadyExistsdException extends RuntimeException {

    public AccountAlreadyExistsdException(String email) {
        super("Account with e-mail: " + email + " already exists");
    }
    
}
