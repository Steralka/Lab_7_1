package labs.lab7.server.utility;

import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.CommandManager;

import java.util.concurrent.Callable;

public class RequestHandler {

    private final CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Callable<Response> handleAsync(Request request) {
        return () -> {
            var command = commandManager.getCommandByName(request.getName());
            if (command == null) return new ErrorResponse("Такой команды нет");
            commandManager.addToHistory(command.getName(), request.getUser());
            return command.apply(request);
        };
    }
}
