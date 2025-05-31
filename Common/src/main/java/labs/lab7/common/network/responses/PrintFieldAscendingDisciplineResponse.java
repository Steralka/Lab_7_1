package labs.lab7.common.network.responses;

import labs.lab7.common.models.Discipline;
import labs.lab7.common.utility.CommandType;

import java.util.List;

public class PrintFieldAscendingDisciplineResponse extends Response {

    private final List<Discipline> disciplines;

    public PrintFieldAscendingDisciplineResponse(List<Discipline> disciplines, String errorMessage) {
        super(CommandType.PRINT_FIELD_ASCENDING_DISCIPLINE.name(), errorMessage);
        this.disciplines = disciplines;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }
}
