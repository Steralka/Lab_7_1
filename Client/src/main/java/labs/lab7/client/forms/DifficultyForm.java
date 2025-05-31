package labs.lab7.client.forms;

import labs.lab7.common.models.Difficulty;
import labs.lab7.common.utility.Console;
import labs.lab7.common.utility.Form;

import java.util.NoSuchElementException;

public class DifficultyForm extends Form<Difficulty> {

    private final Console console;

    public DifficultyForm(Console console) {
        this.console = console;
    }

    @Override
    protected Difficulty create() {
        try {
            return askDifficulty();
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private Difficulty askDifficulty() throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask("Difficulty (" + Difficulty.names() + "): ");
            String input = console.readln().trim();
            if (input.isEmpty()) {
                console.println("Difficulty не может быть равен null");
                continue;
            }
            try {
                return Difficulty.valueOf(input.toUpperCase());
            } catch (NullPointerException | IllegalArgumentException e) {
                console.println("Difficulty с таким названием отсутствует");
            }
        }
    }
}
