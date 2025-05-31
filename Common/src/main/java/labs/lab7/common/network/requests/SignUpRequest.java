package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class SignUpRequest extends Request {

    public SignUpRequest(User user) {
        super(CommandType.SIGN_UP.name(), user);
    }
}
