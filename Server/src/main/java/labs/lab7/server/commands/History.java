package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.HistoryRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.HistoryResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CommandManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Команда 'history'. Выводит историю команд.
 */
public class History extends Command {
    private static final int COUNT_DISPLAY_COMMAND = 15;
    private final CommandManager commandManager;

    public History(CommandManager commandManager) {
        super("history", "выводит историю команд");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof HistoryRequest)) return new HistoryResponse(
                null, "Неверный аргумент комманды"
        );
        try {
            checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        int displaySize = ((HistoryRequest) request).getCommandCount();

        List<String> commandHistory = commandManager.getCommandHistory(request.getUser()).stream()
                .skip(Math.max(0, commandManager.getCommands().size() - displaySize))
                .collect(Collectors.toCollection(ArrayList::new));

        Collections.reverse(commandHistory);
        return new HistoryResponse(commandHistory, "");
    }
}
