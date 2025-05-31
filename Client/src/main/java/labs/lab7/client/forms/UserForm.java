package labs.lab7.client.forms;

import labs.lab7.common.models.User;
import labs.lab7.common.utility.Console;
import labs.lab7.common.utility.Form;

import java.util.NoSuchElementException;
import java.util.Objects;

public class UserForm extends Form<User> {

    private final Console console;

    public UserForm(Console console) {
        this.console = console;
    }

    @Override
    protected User create() {
        try {
            return new User(askName(), askPassword());
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private String askName() throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask("Name: ");
            String name = console.readln().trim();
            if (name.isEmpty()) {
                console.println("Name не может быть пустым");
                continue;
            }
            return name;
        }
    }

    private String askPassword() throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask("Password: ");
            String password = Objects.isNull(System.console())
                    ? console.readln().trim()
                    : new String(System.console().readPassword()).trim();
            if (password.isEmpty()) {
                console.println("Password не может быть пустым");
                continue;
            }
            return password;
        }
    }
}
