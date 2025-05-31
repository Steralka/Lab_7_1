package labs.lab7.client.commands;

import labs.lab7.client.forms.UserForm;
import labs.lab7.client.utility.Client;
import labs.lab7.client.utility.SessionHandler;
import labs.lab7.common.exceptions.InvalidFormException;
import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.SignInRequest;
import labs.lab7.common.network.responses.SignInResponse;
import labs.lab7.common.utility.Console;

import java.util.Objects;

/**
 * Комманда 'sign_in'. Вход в аккаунт пользователя
 */
public class SignIn extends Command {
    private final Console console;
    private final Client client;
    private final SessionHandler sessionHandler;

    public SignIn(Console console, Client client, SessionHandler sessionHandler) {
        super("sign_in", "Вход в аккаунт пользователя");
        this.console = console;
        this.client = client;
        this.sessionHandler = sessionHandler;
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

            console.println("* Авторизация... ");
            if (user == null) {
                user = new UserForm(console).build();
            }
            if (Objects.isNull(user)) throw new InvalidFormException();

            var response = client.sendRequest(new SignInRequest(user));
            if (!response.getErrorMessage().isEmpty()) {
                console.printError(response.getErrorMessage());
                return false;
            }
            if (response instanceof SignInResponse) {
                console.println("Успешный вход");
                sessionHandler.setUser(user);
                return true;
            }
            console.printError("Получен неверный ответ на запрос");
            return false;
        } catch (IllegalArgumentException e) {
            console.println(e.getMessage());
            console.println("Использование: '" + getName() + "'");
            return false;
        } catch (InvalidFormException e) {
            console.printError("Непредвиденная ошибка при создании элемента");
            return false;
        } catch (ServerConnectionException e) {
            console.printError(e.getMessage());
            return false;
        }
    }
}
