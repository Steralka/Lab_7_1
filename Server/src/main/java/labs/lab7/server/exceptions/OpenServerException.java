package labs.lab7.server.exceptions;

import java.io.IOException;

public class OpenServerException extends IOException {

    public OpenServerException(String message) {
        super(message);
    }
}
