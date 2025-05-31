package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class SignUpResponse extends Response {

    public SignUpResponse(String errorMessage) {
        super(CommandType.SIGN_UP.name(), errorMessage);
    }
}
