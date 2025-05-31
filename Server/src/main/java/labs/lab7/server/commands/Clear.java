package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.ClearRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ClearResponse;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CollectionManager;

import java.util.Objects;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof ClearRequest)) return new ClearResponse(
                "Неверный аргумент комманды"
        );
        long userId;
        try {
            userId = checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        collectionManager.clear(userId);
        return new ClearResponse("");
    }

}