package labs.lab7.common.models;


import labs.lab7.common.managers.CSVParser;
import labs.lab7.common.utility.Validatable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс, представляющий учебную дисциплину.
 */
public class Discipline implements Validatable, Serializable {
    private final String name;
    private long lectureHours;
    private long practiceHours;

    public Discipline(String name, long lectureHours, long practiceHours) {
        this.name = name;
        this.lectureHours = lectureHours;
        this.practiceHours = practiceHours;
    }

    /**
     * Создаёт {@code Discipline} из трёх аргументов строк.
     * @param s1 первый аргумент (name)
     * @param s2 второй аргумент (lectureHours)
     * @param s3 третий аргумент (practiceHours)
     * @return {@code Discipline}
     * @throws NumberFormatException если возникли проблемы с парсингом
     */
    public static Discipline createFromStrings(String s1, String s2, String s3) throws NumberFormatException {
        return new Discipline(s1, Long.parseLong(s2), Long.parseLong(s3));
    }

    public String getName() {
        return name;
    }

    public long getLectureHours() {
        return lectureHours;
    }

    public void setLectureHours(long lectureHours) {
        this.lectureHours = lectureHours;
    }

    public long getPracticeHours() {
        return practiceHours;
    }

    public void setPracticeHours(long practiceHours) {
        this.practiceHours = practiceHours;
    }

    /**
     * Проверяет соблюдение условий хранения полей.
     * @return результат проверки
     */
    @Override
    public boolean validate() {
        return Objects.nonNull(name) && !name.isEmpty();
    }

    /**
     * Преобразует {@code Discipline} в CSV-строку.
     * @return CSV-строка
     */
    public String toCSVString() {
        return String.join(CSVParser.DELIMITER, name, String.valueOf(lectureHours), String.valueOf(practiceHours));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return lectureHours == that.lectureHours && practiceHours == that.practiceHours && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lectureHours, practiceHours);
    }

    @Override
    public String toString() {
        return "Discipline{" + "name='" + name + '\'' + ", lectureHours=" + lectureHours + ", practiceHours=" + practiceHours + '}';
    }
}
