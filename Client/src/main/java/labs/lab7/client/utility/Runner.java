package labs.lab7.client.utility;


import labs.lab7.client.commands.*;
import labs.lab7.common.utility.CommandType;
import labs.lab7.common.utility.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.*;

/**
 * Класс, реализующий действия программы.
 */
public class Runner {
    private static final int MAX_RECURSIVE_DEPTH = 2;
    private final Console console;
    private final EnumMap<CommandType, Command> commands;
    private final Client client;
    private int scriptRecursiveDepth;

    private final SessionHandler sessionHandler;

    public Runner(Console console, Client client) {
        this.console = console;
        this.scriptRecursiveDepth = 0;
        this.client = client;
        sessionHandler = new SessionHandler();
        this.commands = new EnumMap<>(CommandType.class) {{
            put(CommandType.ADD, new Add(console, client));
            put(CommandType.ADD_IF_MAX, new AddIfMax(console, client));
            put(CommandType.CLEAR, new Clear(console, client));
            put(CommandType.COUNT_BY_MINIMAL_POINT, new CountByMinimalPoint(console, client));
            put(CommandType.EXECUTE_SCRIPT, new ExecuteScript(console));
            put(CommandType.EXIT, new Exit(console));
            put(CommandType.HELP, new Help(console, client));
            put(CommandType.HISTORY, new History(console, client));
            put(CommandType.INFO, new Info(console, client));
            put(CommandType.PRINT_FIELD_ASCENDING_DISCIPLINE, new PrintFieldAscendingDiscipline(console, client));
            put(CommandType.PRINT_FIELD_DESCENDING_DIFFICULTY, new PrintFieldDescendingDifficulty(console, client));
            put(CommandType.REMOVE_BY_ID, new RemoveById(console, client));
            put(CommandType.REMOVE_LOWER, new RemoveLower(console, client));
            put(CommandType.SHOW, new Show(console, client));
            put(CommandType.UPDATE, new Update(console, client));
            put(CommandType.SIGN_IN, new SignIn(console, client, sessionHandler));
            put(CommandType.SIGN_UP, new SignUp(console, client, sessionHandler));
        }};
    }

    /**
     * Коды возврата команд.
     */
    public enum ExitCode {
        OK, ERROR, RECURSIVE_ERROR, EXIT
    }

    /**
     * Интерактивный режим
     */
    public void interactiveMode() {
        ExitCode commandStatus;
        String[] userCommand;
        try {
            do {
                if (Objects.isNull(sessionHandler.getUser())) {
                    console.println("Для работы необходимо войти в аккаунт 'sign_in' или зарегистрироваться 'sign_up'");
                }
                console.prompt();
                userCommand = console.readln().trim().split("\\s+");
                commandStatus = launchCommand(userCommand);
            } while (commandStatus != ExitCode.EXIT);
        } catch (NoSuchElementException e) {
            console.printError("Входная строка отсутствует");

            commandStatus = ExitCode.ERROR;
        } catch (IllegalStateException e) {
            console.printError("Непредвиденная ошибка");
            System.exit(0);
        }
        finally {
            client.disconnect();
        }
    }


    /**
     * Режим для запуска скрипта.
     *
     * @param fileName полное имя файла
     * @return код завершения
     */
    public ExitCode scriptMode(String fileName) {
        if (++scriptRecursiveDepth == MAX_RECURSIVE_DEPTH) {
            console.println("Достигнута максимальная глубина рекурсии (" + MAX_RECURSIVE_DEPTH + "). " +
                    "Принудительное завершение скрипта.");
            return ExitCode.RECURSIVE_ERROR;
        }

        String[] userCommand;
        ExitCode commandStatus;

        try {
            Path path = Path.of(fileName);
            if (Files.notExists(path)) {
                console.println("Файл не существует");
                return ExitCode.ERROR;
            }
            if (!Files.isReadable(path)) {
                console.println("Прав для чтения нет");
                return ExitCode.ERROR;
            }
        } catch (InvalidPathException e) {
            console.println(fileName + " является некорректным путём");
            return ExitCode.ERROR;
        } catch (SecurityException e) {
            console.printError(e.getMessage());
            return ExitCode.ERROR;
        }

        try (Scanner scriptScanner = new Scanner(new File(fileName))) {
            console.selectFileScanner(scriptScanner);

            commandStatus = ExitCode.OK;
            while (commandStatus == ExitCode.OK && console.canReadln()) {
                userCommand = console.readln().trim().split("\\s+");
                commandStatus = launchCommand(userCommand);
            }

            console.selectConsoleScanner();
            if (commandStatus == ExitCode.OK) {
                console.println("Скрипт успешно выполнен");
            } else if (commandStatus == ExitCode.ERROR) {
                console.println("Проверьте скрипт на корректность введённых данных");
            }
            return commandStatus;
        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка");
            System.exit(0);
        } finally {
            console.selectConsoleScanner();
        }

        return ExitCode.ERROR;
    }

    /**
     * Осуществляет запуск команды.
     * @param userCommand команды для запуска
     * @return код завершения
     */
    private ExitCode launchCommand(String[] userCommand) {
        String commandName = userCommand[0].toUpperCase();
        Command command = null;
        if (CommandType.names().contains(commandName)) {
            command = commands.get(CommandType.valueOf(commandName));
        }
        if (Objects.isNull(command)) {
            console.println("Команда '" + commandName + "' не найдена. Наберите 'help' для справки");
            return ExitCode.ERROR;
        }

        String[] args = Arrays.copyOfRange(userCommand, 1, userCommand.length);
        if (command.apply(args, sessionHandler.getUser())) {
            return switch (commandName) {
                case "EXIT" -> ExitCode.EXIT;
                case "EXECUTE_SCRIPT" -> {
                    ExitCode exitCode = scriptMode(String.join("", args));
                    scriptRecursiveDepth = 0;
                    yield exitCode;
                }
                default -> ExitCode.OK;
            };
        } else {
            return ExitCode.ERROR;
        }
    }
}
