package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.models.Difficulty;
import labs.lab7.common.network.requests.PrintFieldDescendingDifficultyRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.PrintFieldDescendingDifficultyResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CollectionManager;

import java.util.List;
import java.util.Objects;

/**
 * Команда 'print_field_descending_difficulty'. Выводит значение поля {@code difficulty} всех элементов в порядке убывания.
 */
public class PrintFieldDescendingDifficulty extends Command {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingDifficulty(CollectionManager collectionManager) {
        super("print_field_descending_difficulty", "вывести значение поля difficulty всех элементов " +
                "в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof PrintFieldDescendingDifficultyRequest)) {
            return new PrintFieldDescendingDifficultyResponse(
                    null, "Неверный аргумент комманды"
            );
        }
        try {
            checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }

        List<Difficulty> list = collectionManager.getFieldDescendingDifficulty();
        return new PrintFieldDescendingDifficultyResponse(list, "");
    }
}
