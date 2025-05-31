package labs.lab7.server.commands;

import labs.lab7.common.models.User;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.requests.SignUpRequest;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.common.network.responses.SignUpResponse;
import labs.lab7.server.managers.DatabaseManager;

import java.util.Objects;

/**
 * Регистрация
 */
public class SignUp extends Command {

    public SignUp() {
        super("sign_up", "регистрация");
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof SignUpRequest)) {
            return new ErrorResponse("Неверный аргумент команды");
        }
        try {
            var db = DatabaseManager.getInstance();
            User user = request.getUser();
            if (Objects.isNull(user) || !user.validate()) {
                throw new IllegalArgumentException("Отсутствуют данные для авторизации");
            }
            db.signUpUser(user.name(), user.password());
            return new SignUpResponse("");
        } catch (IllegalArgumentException e) {
            return new ErrorResponse(e.getMessage());
        } catch (Exception e) {
            // Логируем и возвращаем общий ответ об ошибке
            System.err.println("Ошибка при регистрации: " + e.getMessage());
            return new ErrorResponse("Ошибка при регистрации пользователя");
        }
    }
}