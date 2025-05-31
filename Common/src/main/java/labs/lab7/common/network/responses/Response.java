package labs.lab7.common.network.responses;

import java.io.Serializable;
import java.util.Objects;

public class Response implements Serializable {

    private final String name;
    private final String errorMessage;

    public Response(String name, String error) {
        this.name = name;
        this.errorMessage = error;
    }

    public String getName() {
        return name;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(name, response.name) && Objects.equals(errorMessage, response.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, errorMessage);
    }

    @Override
    public String toString() {
        return "Response{" +
                "name='" + name + '\'' +
                ", error='" + errorMessage + '\'' +
                '}';
    }
}
