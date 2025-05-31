package labs.lab7.client.commands;


import labs.lab7.client.utility.Client;
import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.CountByMinimalPointRequest;
import labs.lab7.common.network.responses.CountByMinimalPointResponse;
import labs.lab7.common.utility.Console;

/**
 * Команда 'count_by_minimal_point'. Выводит количество элементов, значение поля {@code minimalPoint} которых равно заданному.
 */
public class CountByMinimalPoint extends Command {
    private final Console console;
    private final Client client;

    public CountByMinimalPoint(Console console, Client client) {
        super("count_by_minimal_point minimalPoint", "вывести количество элементов, значение поля " +
                "minimalPoint которых равно заданному");
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
            if (args.length > 1) throw new IllegalArgumentException("Неверное количество аргументов");

            Double targetMinimalPoint = args.length == 0 ? null : Double.parseDouble(args[0]);

            var response = client.sendRequest(new CountByMinimalPointRequest(targetMinimalPoint, user));
            if (!response.getErrorMessage().isEmpty()) {
                console.printError(response.getErrorMessage());
                return false;
            }
            if (response instanceof CountByMinimalPointResponse countByMinimalPointResponse) {
                console.println("Количество элементов: " + countByMinimalPointResponse.getCount());
                return true;
            }
            console.printError("Получен неверный ответ на запрос");
            return false;
        } catch (NumberFormatException e) {
            console.println("Аргумент '" + args[0] + "' не является типом Double");
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
