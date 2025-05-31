package labs.lab7.common.exceptions;

public class AuthorizationException extends IllegalAccessException {

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
