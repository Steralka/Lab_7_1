package labs.lab7.server.commands;

import labs.lab7.common.exceptions.AuthorizationException;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.requests.SignInRequest;
import labs.lab7.common.network.responses.ErrorResponse;
import labs.lab7.common.network.responses.Response;
import labs.lab7.common.network.responses.SignInResponse;

import java.util.Objects;

/**
 * Авторизация
 */
public class SignIn extends Command {

    public SignIn() {
        super("sign_id", "авторизация");
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param request запрос на выполнение команды
     * @return Ответ с результатом выполнения команды
     */
    @Override
    public Response apply(Request request) {
        if (Objects.isNull(request) || !(request instanceof SignInRequest)) return new SignInResponse(
                "Неверный аргумент комманды"
        );
        try {
            checkAuthorization(request.getUser());
        } catch (AuthorizationException e) {
            return new ErrorResponse(e.getMessage());
        }
        return new SignInResponse("");
    }
}
