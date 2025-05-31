package labs.lab7.common.exceptions;

import java.io.IOException;

public class ServerConnectionException extends IOException {

    public ServerConnectionException(String message) {
        super(message);
    }
}
