package labs.lab7.server.commands;

import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.InfoRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.InfoResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CollectionManager;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof InfoRequest)) return new InfoResponse(
                "", "Неверный аргумент комманды"
        );

        try {
            checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии ещё не происходило сохранения" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии ещё не происходило инициализации" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        String infoMessage = "Сведения о коллекции:" + System.lineSeparator() +
                "Тип: " + collectionManager.getCollection().getClass() + System.lineSeparator() +
                "Количество элементов: " + collectionManager.size() + System.lineSeparator() +
                "Дата последнего сохранения: " + lastSaveTimeString + System.lineSeparator() +
                "Дата последней инициализации: " + lastInitTimeString + System.lineSeparator();
        return new InfoResponse(infoMessage, "");
    }
}
