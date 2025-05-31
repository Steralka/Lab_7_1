package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.models.Discipline;
import labs.lab7.common.network.requests.PrintFieldAscendingDisciplineRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.PrintFieldAscendingDisciplineResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CollectionManager;

import java.util.List;
import java.util.Objects;

/**
 * Команда 'print_field_ascending_discipline'. Выводит значение поля {@code discipline} всех элементов в порядке возрастания.
 */
public class PrintFieldAscendingDiscipline extends Command {
    private final CollectionManager collectionManager;

    public PrintFieldAscendingDiscipline(CollectionManager collectionManager) {
        super("print_field_ascending_discipline", "вывести значение поля discipline всех элементов " +
                "в порядке возрастания");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof PrintFieldAscendingDisciplineRequest)) {
            return new PrintFieldAscendingDisciplineResponse(
                    null, "Неверный аргумент комманды"
            );
        }
        try {
            checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        List<Discipline> list = collectionManager.getFieldAscendingDiscipline();
        return new PrintFieldAscendingDisciplineResponse(list, "");
    }
}
