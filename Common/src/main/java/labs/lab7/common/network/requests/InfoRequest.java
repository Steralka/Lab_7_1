package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class InfoRequest extends Request {

    public InfoRequest(User user) {
        super(CommandType.INFO.name(), user);
    }
}
