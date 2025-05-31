package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class RemoveByIdRequest extends Request {

    private final long id;

    public RemoveByIdRequest(long id, User user) {
        super(CommandType.REMOVE_BY_ID.name(), user);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
