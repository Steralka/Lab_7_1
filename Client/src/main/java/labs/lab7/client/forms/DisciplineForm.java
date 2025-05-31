package labs.lab7.client.forms;

import labs.lab7.common.models.Discipline;
import labs.lab7.common.utility.Console;
import labs.lab7.common.utility.Form;

import java.util.NoSuchElementException;

public class DisciplineForm extends Form<Discipline> {

    private final Console console;

    public DisciplineForm(Console console) {
        this.console = console;
    }

    @Override
    protected Discipline create() {
        try {
            console.ask("Discipline (введите пустую строку для значения null, либо что угодно иначе): ");
            if (console.readln().isEmpty()) {
                return null;
            }
            return new Discipline(askName(), askHours("lectureHours"), askHours("practiceHours"));
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private String askName() throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask("nameDiscipline: ");
            String name = console.readln().trim();
            if (name.isEmpty()) {
                console.println("nameDiscipline не может быть равен null и пустой строке");
                continue;
            }
            return name;
        }
    }

    private long askHours(String hours) throws NoSuchElementException, IllegalStateException {
        while (true) {
            console.ask(hours + ": ");
            String input = console.readln().trim();
            if (input.isEmpty()) {
                console.println(hours + " не может быть равен null");
                continue;
            }
            try {
                long time = Long.parseLong(input);
                if (time < 0) {
                    console.println("Аргумент '" + input + "' не может быть отрицательным");
                    continue;
                }
                return time;
            } catch (NumberFormatException e) {
                console.println("Аргумент '" + input + "' не является типом Long");
            }
        }
    }

}
