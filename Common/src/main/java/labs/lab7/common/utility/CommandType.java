package labs.lab7.common.utility;

import java.util.Arrays;
import java.util.List;

public enum CommandType {
    ADD,
    ADD_IF_MAX,
    CLEAR,
    COUNT_BY_MINIMAL_POINT,
    EXECUTE_SCRIPT,
    EXIT,
    HELP,
    HISTORY,
    INFO,
    PRINT_FIELD_ASCENDING_DISCIPLINE,
    PRINT_FIELD_DESCENDING_DIFFICULTY,
    REMOVE_BY_ID,
    REMOVE_LOWER,
    SAVE,
    SHOW,
    UPDATE,
    SIGN_IN,
    SIGN_UP;


    public static List<String> names() {
        return Arrays.stream(values()).map(CommandType::name).toList();
    }
}
