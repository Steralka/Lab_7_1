package labs.lab7.client.forms;

import labs.lab7.common.models.Coordinates;
import labs.lab7.common.utility.Console;
import labs.lab7.common.utility.Form;

import java.util.NoSuchElementException;

public class CoordinatesForm extends Form<Coordinates> {

    private final Console console;

    public CoordinatesForm(Console console) {
        this.console = console;
    }

    @Override
    protected Coordinates create() {
        try {
            return new Coordinates(askCoordinate("x"), askCoordinate("y"));
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private int askCoordinate(String coordinate) throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask("Coordinates." + coordinate + ": ");
            String input = console.readln().trim();
            if (input.isEmpty()) {
                console.println(coordinate + " не может быть равен null");
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                console.println("Аргумент '" + input + "' не является типом Integer");
            }
        }
    }
}
