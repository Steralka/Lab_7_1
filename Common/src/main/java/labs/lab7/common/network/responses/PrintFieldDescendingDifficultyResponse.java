package labs.lab7.common.network.responses;

import labs.lab7.common.models.Difficulty;
import labs.lab7.common.utility.CommandType;

import java.util.List;

public class PrintFieldDescendingDifficultyResponse extends Response {

    private final List<Difficulty> difficulties;

    public PrintFieldDescendingDifficultyResponse(List<Difficulty> difficulties, String errorMessage) {
        super(CommandType.PRINT_FIELD_DESCENDING_DIFFICULTY.name(), errorMessage);
        this.difficulties = difficulties;
    }

    public List<Difficulty> getDifficulties() {
        return difficulties;
    }
}
