package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.AddIfMaxRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.AddIfMaxResponse;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CollectionManager;

import java.util.Objects;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение
 * наибольшего элемента этой коллекции.
 */
public class AddIfMax extends Command {
    private final CollectionManager collectionManager;

    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof AddIfMaxRequest)) return new AddIfMaxResponse(
                -1L, false, "Неверный аргумент комманды"
        );
        long userId;
        try {
            userId = checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        var labWork = ((AddIfMaxRequest) request).getLabWork();
        if (!collectionManager.isMaxElement(labWork)) return new AddIfMaxResponse(-1L, false, "");

        long id = collectionManager.add(labWork, userId);
        if (id > 0) {
            labWork.setId(id);
            return new AddIfMaxResponse(labWork.getId(), true, "");
        }
        return new AddIfMaxResponse(-1L, false, "Ошибка при добавлении объекта");
    }
}
