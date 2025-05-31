package labs.lab7.common.network.responses;

import labs.lab7.common.models.LabWork;
import labs.lab7.common.utility.CommandType;

import java.util.List;

public class ShowResponse extends Response {

    private final List<LabWork> labWorks;

    public ShowResponse(List<LabWork> labWorks, String errorMessage) {
        super(CommandType.SHOW.name(), errorMessage);
        this.labWorks = labWorks;
    }

    public List<LabWork> getLabWorks() {
        return labWorks;
    }
}
