package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class ShowRequest extends Request {

    public ShowRequest(User user) {
        super(CommandType.SHOW.name(), user);
    }
}
