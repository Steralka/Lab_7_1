package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class ClearRequest extends Request {

    public ClearRequest(User user) {
        super(CommandType.CLEAR.name(), user);
    }
}
