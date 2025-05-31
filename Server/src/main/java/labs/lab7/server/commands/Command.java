package labs.lab7.server.commands;

import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.Response;
import labs.lab7.server.managers.DatabaseManager;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Абстрактная команда с именем и описанием.
 */
public abstract class Command {
    private final String name;
    private final String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return Название и использование команды
     */
    public String getName() {
        return name;
    }

    /**
     * @return Описание команды
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
        return "Command{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    public abstract Response apply(Request request);

    protected long checkAuthorization(User user) throws AuthorizationException {
        try {
            var db = DatabaseManager.getInstance();
            if (Objects.isNull(user) || !user.validate()) throw new IllegalArgumentException("Отсутствуют данные для авторизации");
            return db.signInUser(user.name(), user.password());
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new AuthorizationException(e.getMessage());
        }
    }
}
