package labs.lab7.client.forms;

import labs.lab7.common.models.LabWork;
import labs.lab7.common.utility.Console;
import labs.lab7.common.utility.Form;

import java.util.NoSuchElementException;

public class LabWorkForm extends Form<LabWork> {

    private final Console console;

    public LabWorkForm(Console console) {
        this.console = console;
    }

    @Override
    protected LabWork create() {
        try {
            var name = askName();
            var coordinates = new CoordinatesForm(console).create();
            var minimalPoint = askMinimalPoint();
            var difficulty = new DifficultyForm(console).create();
            var discipline = new DisciplineForm(console).create();

            return new LabWork(0L, name, coordinates, minimalPoint, difficulty, discipline);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private String askName() throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask("nameLabWork: ");
            String name = console.readln().trim();
            if (name.isEmpty()) {
                console.println("nameLabWork не может быть равен null и пустой строке");
                continue;
            }
            return name;
        }
    }

    private Double askMinimalPoint() throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask("minimalPoint: ");
            String input = console.readln().trim();
            if (input.isEmpty()) {
                return null;
            }
            try {
                double minimalPoint = Double.parseDouble(input);
                if (minimalPoint <= 0) {
                    console.println("minimalPoint должен быть положительным");
                    continue;
                }
                return minimalPoint;
            } catch (NumberFormatException e) {
                console.println("Аргумент '" + input + "' не является типом Double");
            }
        }
    }

}
