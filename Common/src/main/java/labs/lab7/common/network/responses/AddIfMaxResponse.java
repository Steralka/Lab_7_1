package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class AddIfMaxResponse extends Response {

    private final long id;
    private final boolean isAdded;

    public AddIfMaxResponse(long id, boolean isAdded, String errorMessage) {
        super(CommandType.ADD_IF_MAX.name(), errorMessage);
        this.id = id;
        this.isAdded = isAdded;
    }

    public long getId() {
        return id;
    }

    public boolean isAdded() {
        return isAdded;
    }
}
