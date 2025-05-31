package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class AddResponse extends Response {

    private final long id;

    public AddResponse(long id, String errorMessage) {
        super(CommandType.ADD.name(), errorMessage);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
