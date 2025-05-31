package labs.lab7.common.network.requests;

import labs.lab7.common.models.LabWork;
import labs.lab7.common.models.User;
import labs.lab7.common.utility.CommandType;

public class AddIfMaxRequest extends Request {

    private final LabWork labWork;

    public AddIfMaxRequest(LabWork labWork, User user) {
        super(CommandType.ADD.name(), user);
        this.labWork = labWork;
    }

    public LabWork getLabWork() {
        return labWork;
    }
}
