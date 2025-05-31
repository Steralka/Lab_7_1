package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class HistoryRequest extends Request {

    private final int commandCount;

    public HistoryRequest(int commandCount, User user) {
        super(CommandType.HISTORY.name(), user);
        this.commandCount = commandCount;
    }

    public int getCommandCount() {
        return commandCount;
    }
}
