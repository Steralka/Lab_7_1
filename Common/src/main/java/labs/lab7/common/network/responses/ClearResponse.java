package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class ClearResponse extends Response {

    public ClearResponse(String errorMessage) {
        super(CommandType.CLEAR.name(), errorMessage);
    }
}
