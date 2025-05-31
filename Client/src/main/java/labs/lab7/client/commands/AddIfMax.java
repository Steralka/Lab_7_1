package labs.lab7.client.commands;


import labs.lab7.client.forms.LabWorkForm;
import labs.lab7.client.utility.Client;
import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.exceptions.InvalidFormException;
import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.models.LabWork;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.AddIfMaxRequest;
import labs.lab7.common.network.responses.AddIfMaxResponse;
import labs.lab7.common.utility.Console;

import java.util.Objects;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение
 * наибольшего элемента этой коллекции.
 */
public class AddIfMax extends Command {
    private final Console console;
    private final Client client;

    public AddIfMax(Console console, Client client) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции");
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

            console.println("* Создание... ");
            LabWork newLab = new LabWorkForm(console).build();
            if (Objects.isNull(newLab)) throw new InvalidFormException();

            var response = client.sendRequest(new AddIfMaxRequest(newLab, user));
            if (!response.getErrorMessage().isEmpty()) {
                console.printError(response.getErrorMessage());
                return false;
            }
            if (response instanceof AddIfMaxResponse addIfMaxResponse) {
                if (addIfMaxResponse.isAdded()) {
                    console.println("Новый объект с id=" + addIfMaxResponse.getId() + " успешно добавлен!");
                } else {
                    console.println("Элемент не добавлен, поскольку он не является максимальным");
                }
                return true;
            }
            console.printError("Получен неверный ответ на запрос");
            return false;
        } catch (IllegalArgumentException e) {
            console.println(e.getMessage());
            console.println("Использование: '" + getName() + "'");
            return false;
        } catch (InvalidFormException e) {
            console.println("Непредвиденная ошибка при создании элемента");
            return false;
        } catch (ServerConnectionException e) {
            console.println(e.getMessage());
            return false;
        }
    }
}
