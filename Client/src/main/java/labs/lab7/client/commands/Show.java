package labs.lab7.client.commands;


import labs.lab7.client.utility.Client;
import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.models.LabWork;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.ShowRequest;
import labs.lab7.common.network.responses.ShowResponse;
import labs.lab7.common.utility.Console;

import java.util.List;

/**
 * Команда 'show'. Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */
public class Show extends Command {
    private final Console console;
    private final Client client;

    public Show(Console console, Client client) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
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

            var response = client.sendRequest(new ShowRequest(user));
            if (!response.getErrorMessage().isEmpty()) {
                console.printError(response.getErrorMessage());
            }

            if (response instanceof ShowResponse showResponse) {
                List<LabWork> labWorks = showResponse.getLabWorks();
                if (labWorks.isEmpty()) {
                    console.println("Коллекция пустая");
                    return true;
                }
                labWorks.forEach(console::println);
                return true;
            }
            console.printError("Получен неверный ответ на запрос");
            return false;
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
            console.println("Использование: '" + getName() + "'");
            return false;
        } catch (ServerConnectionException e) {
            console.printError(e.getMessage());
            return false;
        }
    }
}
