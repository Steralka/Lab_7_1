package labs.lab7.common.network.requests;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class PrintFieldAscendingDisciplineRequest extends Request {

    public PrintFieldAscendingDisciplineRequest(User user) {
        super(CommandType.PRINT_FIELD_ASCENDING_DISCIPLINE.name(), user);
    }
}
