package labs.lab7.common.utility;

import labs.lab7.common.exceptions.InvalidFormException;

/**
 * Абстрактный класс для ввода пользовательских данных
 * @param <T> класс создоваемого объекта
 */
public abstract class Form<T extends Validatable> {

    protected abstract T create();

    public final T build() throws InvalidFormException {
        T result = create();
        if (result != null && !result.validate()) throw new InvalidFormException();
        return result;
    }
}
