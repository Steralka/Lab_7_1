package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class SaveResponse extends Response {

    public SaveResponse(String errorMessage) {
        super(CommandType.SAVE.name(), errorMessage);
    }
}
