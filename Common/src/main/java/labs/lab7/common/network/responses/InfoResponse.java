package labs.lab7.common.network.responses;

import labs.lab7.common.utility.CommandType;

public class InfoResponse extends Response {

    private final String infoMessage;

    public InfoResponse(String infoMessage, String errorMessage) {
        super(CommandType.INFO.name(), errorMessage);
        this.infoMessage = infoMessage;
    }

    public String getInfoMessage() {
        return infoMessage;
    }
}
