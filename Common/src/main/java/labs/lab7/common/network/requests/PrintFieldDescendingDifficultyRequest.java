package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class PrintFieldDescendingDifficultyRequest extends Request {

    public PrintFieldDescendingDifficultyRequest(User user) {
        super(CommandType.PRINT_FIELD_DESCENDING_DIFFICULTY.name(), user);
    }
}
