package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.AddRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.AddResponse;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CollectionManager;

import java.util.Objects;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {

    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof AddRequest)) return new AddResponse(
                -1L, "Неверный аргумент комманды"
        );
        long userId;
        try {
            userId = checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        var labWork = ((AddRequest) request).getLabWork();
        long id = collectionManager.add(labWork, userId);
        if (id >= 0) {
            labWork.setId(id);
            System.out.println("Добавлен новый объект");
            return new AddResponse(labWork.getId(), "");
        }
        else{
            return new AddResponse(-1L, "Ошибка при добавлении объекта");
        }
    }
}
