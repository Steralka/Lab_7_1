package labs.lab7.client.commands;


import labs.lab7.client.utility.Client;
import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.InfoRequest;
import labs.lab7.common.network.responses.InfoResponse;
import labs.lab7.common.utility.Console;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final Console console;
    private final Client client;

    public Info(Console console, Client client) {
        super("info", "вывести информацию о коллекции");
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

            var response = client.sendRequest(new InfoRequest(user));
            if (!response.getErrorMessage().isEmpty()) {
                console.printError(response.getErrorMessage());
                return false;
            }

            if (response instanceof InfoResponse infoResponse) {
                console.println(infoResponse.getInfoMessage());
                return true;
            }
            console.printError("Получен неверный ответ на запрос");
            return false;
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
            console.println("использование: '" + getName() + "'");
            return false;
        } catch (ServerConnectionException e) {
            console.printError(e.getMessage());
            return false;
        }
    }
}
