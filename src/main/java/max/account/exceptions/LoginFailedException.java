package max.account.exceptions;

public class LoginFailedException extends RuntimeException{
    
    public LoginFailedException(String email) {
        super("Login failed for email: " + email);
    }

}
