package labs.lab7.common.models;

import labs.lab7.common.utility.Validatable;

import java.io.Serializable;

public record User(String name, String password) implements Serializable, Validatable {

    @Override
    public boolean validate() {
        return name != null && !name.isBlank() && password != null && !password.isBlank();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
