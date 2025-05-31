package labs.lab7.client.commands;


import labs.lab7.client.utility.Client;
import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.HistoryRequest;
import labs.lab7.common.network.responses.HistoryResponse;
import labs.lab7.common.utility.Console;

import java.util.List;

/**
 * Команда 'history'. Выводит историю команд.
 */
public class History extends Command {
    private static final int COUNT_DISPLAY_COMMAND = 15;
    private final Console console;
    private final Client client;

    public History(Console console, Client client) {
        super("history", "выводит историю команд");
        this.console = console;
        this.client = client;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param args аргументы команды
     * @param user текущий пользователь
     * @return Успешность выполнения команды
     */
    @Override
    public boolean apply(String[] args, User user) {
        try {
            if (args.length > 0) throw new IllegalArgumentException("Неправильное количество аргументов");

            var response = client.sendRequest(new HistoryRequest(COUNT_DISPLAY_COMMAND, user));
            if (!response.getErrorMessage().isEmpty()) {
                console.printError(response.getErrorMessage());
                return false;
            }

            if (response instanceof HistoryResponse historyResponse) {
                List<String> commandHistory = historyResponse.getHistoryMessages();
                if (commandHistory.isEmpty()) {
                    console.println("История команд пуста");
                    return true;
                }
                console.println("Последние " + commandHistory.size() + " команд:");
                commandHistory.forEach(console::println);
                return true;
            }
            console.printError("Получен неверный ответ на запрос");
            return false;
        } catch (IllegalArgumentException e) {
            console.println(e.getMessage());
            console.println("Использование: '" + getName() + "'");
            return false;
        } catch (ServerConnectionException e) {
            console.printError(e.getMessage());
            return false;
        }
    }
}
