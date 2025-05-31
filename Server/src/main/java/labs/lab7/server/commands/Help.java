package labs.lab7.server.commands;


import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.HelpRequest;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.HelpResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.common.utility.CommandInfo;
import labs.lab7.server.managers.CommandManager;

import java.util.Comparator;
import java.util.Objects;

/**
 * Команда 'help'. Выводит справку по доступным командам.
 */
public class Help extends Command {
    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof HelpRequest)) return new HelpResponse(
                null, "Неверный аргумент комманды"
        );
        try {
            checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        var commands = commandManager.getCommands().values().stream()
                .map(command -> new CommandInfo(command.getName(), command.getDescription()))
                .sorted(Comparator.comparing(CommandInfo::name))
                .toList();
        return new HelpResponse(commands, "");
    }
}
