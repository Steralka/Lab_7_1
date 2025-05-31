package labs.lab7.server.managers;


import labs.lab7.common.models.User;
import labs.lab7.server.commands.Command;

import java.util.*;

/**
 * Класс, управляющий коллекцией объектов класса {@link Command}.
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final List<CommandInfo> commandHistory = new ArrayList<>();

    /**
     * Добавляет команду.
     * @param commandName Название команды
     * @param command Команда
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return Словарь команд
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Отображение из имени команды в объект {@code Command}.
     * @param name искомое имя команды
     * @return null, в случае отсутствия {@code name}, и {@code Command} в ином случае
     */
    public Command getCommandByName(String name) {
        if (Objects.isNull(name)) {
            return null;
        }
        return commands.get(name);
    }

    /**
     * @return История команд
     */
    public List<String> getCommandHistory(User user) {
        return commandHistory.stream()
                .filter(info -> info.username().equals(user.name()))
                .map(CommandInfo::command)
                .toList();
    }

    /**
     * Добавляет команду в историю.
     * @param command команда
     */
    public void addToHistory(String command, User user) {
        commandHistory.add(new CommandInfo(user.name(), command));
    }

    /**
     * Класс для хранения информации о том кто использовал команду
     * @param username имя пользователя
     * @param command название команды
     */
    private record CommandInfo(String username, String command) {}
}
