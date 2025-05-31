package labs.lab7.common.models;

import labs.lab7.common.utility.Validatable;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Перечисление уровней сложности {@link LabWork}.
 */
public enum Difficulty implements Serializable, Validatable {
    EASY,
    NORMAL,
    VERY_HARD,
    HOPELESS,
    TERRIBLE;

    /**
     * Соединяет все уровни сложности в одну строку.
     * @return итоговая строка
     */
    public static String names() {
        return String.join(", ", Arrays.stream(values()).map(Enum::name).toList());
    }


    @Override
    public boolean validate() {
        return true;
    }
}
