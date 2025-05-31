package labs.lab7.client.commands;


import labs.lab7.client.utility.Client;
import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.ClearRequest;
import labs.lab7.common.network.responses.ClearResponse;
import labs.lab7.common.utility.Console;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear extends Command {
    private final Console console;
    private final Client client;

    public Clear(Console console, Client client) {
        super("clear", "очистить коллекцию");
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
            if (args.length > 0) throw new IllegalArgumentException("Неверное количество аргументов");

            var response = client.sendRequest(new ClearRequest(user));
            if (!response.getErrorMessage().isEmpty()) {
                console.printError(response.getErrorMessage());
                return false;
            }
            if (response instanceof ClearResponse) {
                console.println("Коллекция очищена");
                return true;
            }
            console.printError("Получен неверный ответ на запрос");
            return false;
        } catch (IllegalArgumentException e) {
            console.println(e.getMessage());
            console.println("Использование: '" + getName() + "'");
            return false;
        } catch (ServerConnectionException e) {
            console.println(e.getMessage());
            return false;
        }
    }

}