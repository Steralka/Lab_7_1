package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.requests.UpdateRequest;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.common.network.responses.UpdateResponse;
import labs.lab7.server.managers.CollectionManager;

import java.util.Objects;

/**
 * Команда 'update'. Обновляет значение элемента по {@code id}.
 */
public class Update extends Command {
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update {id}", "обновить значение элемента по id");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof UpdateRequest)) return new UpdateResponse(
                "Неверный аргумент комманды"
        );
        long userId;
        try {
            userId = checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }

        var labWork = ((UpdateRequest) request).getLabWork();

        if (Objects.isNull(labWork)) {
            return new UpdateResponse("Пустой объект");
        }

        if (!collectionManager.contains(labWork.getId())) {
            return new UpdateResponse("Элемента с id = " + labWork.getId() + " не найдено");
        }

        if (collectionManager.update(labWork, userId)) {
            return new UpdateResponse("");
        }

        return new UpdateResponse("Ошибка при обновлении объекта");
    }
}

