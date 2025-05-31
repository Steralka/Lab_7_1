package labs.lab7.client.commands;


import labs.lab7.common.models.User;
import labs.lab7.common.utility.Console;

/**
 * Команда 'execute_script'. Исполняет скрипт из указанного файла.
 */
public class ExecuteScript extends Command {
    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script file_name", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    /**
     * Выполняет команду при заданных аргументах.
     * @param args аргументы команды
     * @param user текущий пользователь
     * @return Успешность выполнения команды
     */
    @Override
    public boolean apply(String[] args, User user) {
        if (args.length == 0) {
            console.println("Неверное количество аргументов.");
            console.println("Использование: '" + getName() + "'.");
            return false;
        }

        console.println("Выполнение скрипта...");
        return true;
    }
}
