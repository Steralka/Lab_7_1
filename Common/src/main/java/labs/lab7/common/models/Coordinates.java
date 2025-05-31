package labs.lab7.common.models;

import labs.lab7.common.managers.CSVParser;
import labs.lab7.common.utility.Validatable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс координат.
 */
public class Coordinates implements Validatable, Serializable {
    private Integer x;
    private int y;

    public Coordinates(Integer x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Создаёт {@code Coordinates} из двух аргументов строк.
     * @param s1 первый аргумент (x)
     * @param s2 второй аргумент (y)
     * @return {@code Coordinates}
     * @throws NumberFormatException если возникли проблемы с парсингом
     */
    public static Coordinates createFromStrings(String s1, String s2) throws NumberFormatException {
        return new Coordinates(Integer.parseInt(s1), Integer.parseInt(s2));
    }

    /**
     * Проверяет соблюдение условий хранения полей.
     * @return результат проверки
     */
    @Override
    public boolean validate() {
        return Objects.nonNull(x);
    }

    /**
     * Преобразует {@code Coordinates} в CSV-строку.
     * @return CSV-строка
     */
    public String toCSVString() {
        return String.join(CSVParser.DELIMITER, String.valueOf(x), String.valueOf(y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return y == that.y && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" + "x=" + x + ", y=" + y + '}';
    }
}
