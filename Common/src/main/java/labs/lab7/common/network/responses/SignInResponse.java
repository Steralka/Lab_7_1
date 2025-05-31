package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class SignInResponse extends Response {

    public SignInResponse(String errorMessage) {
        super(CommandType.SIGN_IN.name(), errorMessage);
    }
}
