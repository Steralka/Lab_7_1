package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class RemoveByIdResponse extends Response {

    public RemoveByIdResponse(String errorMessage) {
        super(CommandType.REMOVE_BY_ID.name(), errorMessage);
    }
}
