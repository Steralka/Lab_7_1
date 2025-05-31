package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.RemoveByIdRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.RemoveByIdResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CollectionManager;

import java.util.Objects;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по {@code id}.
 */
public class RemoveById extends Command {
    private final CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по id");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof RemoveByIdRequest)) return new RemoveByIdResponse(
                "Неверный аргумент комманды"
        );
        long userId;
        try {
            userId = checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }

        long id = ((RemoveByIdRequest) request).getId();

        if (!collectionManager.contains(id)) {
            return new RemoveByIdResponse("Элемента с id = " + id + " не найдено");
        }

        if (collectionManager.remove(id, userId)) return new RemoveByIdResponse("");

        return new RemoveByIdResponse("Ошибка при удалении объекта");
    }
}
