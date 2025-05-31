package labs.lab7.common.models;


import labs.lab7.common.managers.CSVParser;
import labs.lab7.common.utility.Validatable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Класс, представляющий лабораторную работу.
 */
public class LabWork implements Validatable, Comparable<LabWork>, Serializable {
    private Long id;
    private final String name;
    private final Coordinates coordinates;
    private final ZonedDateTime creationDate;
    private final Double minimalPoint;
    private final Difficulty difficulty;
    private final Discipline discipline;

    private LabWork(Long id,
                    String name,
                    Coordinates coordinates,
                    ZonedDateTime creationDate,
                    Double minimalPoint,
                    Difficulty difficulty,
                    Discipline discipline
    ) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.minimalPoint = minimalPoint;
        this.difficulty = difficulty;
        this.discipline = discipline;
    }

    public LabWork(Long id,
                   String name,
                   Coordinates coordinates,
                   Double minimalPoint,
                   Difficulty difficulty,
                   Discipline discipline
    ) {
        this(id, name, coordinates, ZonedDateTime.now(), minimalPoint, difficulty, discipline);
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return {@code id}
     */
    public Long getId() {
        return id;
    }

    /**
     * @return название {@code name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return координаты {@code сoordinates}
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return время создания {@code сreationDate}
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @return минимальный балл {@code minimalPoint}
     */
    public Double getMinimalPoint() {
        return minimalPoint;
    }

    /**
     * @return сложность {@code difficulty}
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @return дисциплина {@code discipline}
     */
    public Discipline getDiscipline() {
        return discipline;
    }

    /**
     * Создаёт {@code LabWork} из CSV-строки.
     * @param csvLine CSV-строка
     * @return {@code LabWork} в случае успешного парсинга, и {@code null}, иначе
     */
    public static LabWork createFromCSVLine(String csvLine) {
        String[] words = CSVParser.parseCSVLineToTokens(csvLine);
        if (words.length != 8 && words.length != 10) {
            return null;
        }
        try {
            long id = Long.parseLong(words[0]);
            String name = words[1];
            Coordinates coordinates = Coordinates.createFromStrings(words[2], words[3]);
            ZonedDateTime creationDate = ZonedDateTime.parse(words[4]);
            Double minimalPoint = words[5].isEmpty() ? null : Double.parseDouble(words[5]);
            Difficulty difficulty = Difficulty.valueOf(words[6]);

            Discipline discipline;
            if (words.length == 8) {
                if (words[7].isEmpty()) {
                    discipline = null;
                } else {
                    return null;
                }
            } else {
                discipline = Discipline.createFromStrings(words[7], words[8], words[9]);
            }

            LabWork result = new LabWork(id, name, coordinates, creationDate, minimalPoint, difficulty, discipline);
            return result.validate() ? result : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Проверяет соблюдение условий хранения полей.
     * @return результат проверки
     */
    @Override
    public boolean validate() {
        if (Objects.isNull(id) || id < 0) {
            return false;
        }
        if (Objects.isNull(name) || name.isEmpty()) {
            return false;
        }
        if (Objects.isNull(coordinates) || !coordinates.validate()) {
            return false;
        }
        if (Objects.isNull(creationDate)) {
            return false;
        }
        if (Objects.nonNull(minimalPoint) && minimalPoint <= 0) {
            return false;
        }
        if (Objects.isNull(difficulty)) {
            return false;
        }
        return Objects.isNull(discipline) || discipline.validate();
    }

    /**
     * Преобразует {@code LabWork} в CSV-строку.
     * @return CSV-строка
     */
    public String toCSVString() {
        return String.join(
                CSVParser.DELIMITER,
                Objects.isNull(id) ? "" : id.toString(),
                Objects.isNull(name) ? "" : name,
                Objects.isNull(coordinates) ? "" : coordinates.toCSVString(),
                Objects.isNull(creationDate) ? "" : creationDate.toString(),
                Objects.isNull(minimalPoint) ? "" : minimalPoint.toString(),
                Objects.isNull(difficulty) ? "" : difficulty.toString(),
                Objects.isNull(discipline) ? "" : discipline.toCSVString()
        );
    }

    @Override
    public int compareTo(LabWork labWork) {
        if (Objects.isNull(minimalPoint) && Objects.isNull(labWork.getMinimalPoint())) {
            return 0;
        }
        if (Objects.isNull(minimalPoint)) {
            return -1;
        }
        if (Objects.isNull(labWork.getMinimalPoint())) {
            return 1;
        }
        return (int) (minimalPoint - labWork.getMinimalPoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, minimalPoint, difficulty, discipline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabWork labWork = (LabWork) o;
        return Objects.equals(id, labWork.id) &&
                Objects.equals(name, labWork.name) &&
                Objects.equals(coordinates, labWork.coordinates) &&
                Objects.equals(creationDate, labWork.creationDate) &&
                Objects.equals(minimalPoint, labWork.minimalPoint) &&
                difficulty == labWork.difficulty &&
                Objects.equals(discipline, labWork.discipline);
    }

    @Override
    public String toString() {
        return "LabWork{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", minimalPoint=" + minimalPoint +
                ", difficulty=" + difficulty +
                ", discipline=" + discipline +
                '}';
    }
}
