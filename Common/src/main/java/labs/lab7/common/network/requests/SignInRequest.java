package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class SignInRequest extends Request {

    public SignInRequest(User user) {
        super(CommandType.SIGN_IN.name(), user);
    }
}
