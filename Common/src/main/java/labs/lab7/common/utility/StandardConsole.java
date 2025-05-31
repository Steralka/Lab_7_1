package labs.lab7.common.utility;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Для ввода команд и вывода результата
 */
public class StandardConsole implements Console {
    private static final String P = ">>> ";
    private Scanner fileScanner;
    private final Scanner defScanner;

    /**
     * Дефолтный конструктор
     */
    public StandardConsole() {
        fileScanner = null;
        defScanner = new Scanner(System.in);
    }

    /**
     * Выводит obj.toString() в консоль при условии canPrint().
     * @param obj Объект для печати
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит obj.toString() + System.lineSeparator() в консоль при условии canPrint().
     * @param obj Объект для печати
     */
    public void println(Object obj) {
        print(obj);
        System.out.println();
    }

    /**
     * Выводит ошибку: obj.toString() в консоль
     * @param obj Ошибка для печати
     */
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

    /**
     * Считывает строку со стандартного ввода.
     * @return строка
     * @throws NoSuchElementException если нет строки
     * @throws IllegalStateException если getCurrentScanner() закрыт
     */
    public String readln() throws NoSuchElementException, IllegalStateException {
        return getCurrentScanner().nextLine();
    }

    /**
     * Проверяет, можно ли считать следующую строку со стандартного ввода.
     * @return результат проверки
     * @throws IllegalStateException если getCurrentScanner() закрыт
     */
    public boolean canReadln() throws IllegalStateException {
        return getCurrentScanner().hasNextLine();
    }

    /**
     * Задаёт пользователю вопрос. Для этого случая используется именно этот метод (а не {@link #print(Object)}),
     * чтобы при выполнении скрипта в консоль не выводилась лишняя информация.
     * @param question вопрос
     */
    public void ask(String question) {
        if (getCurrentScanner() == defScanner) {
            System.out.print(question);
        }
    }

    /**
     * Выводит 2 колонки при условии canPrint().
     * @param elementLeft левый элемент колонки.
     * @param elementRight правый элемент колонки.
     */
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf("%-50s%-1s%n", elementLeft, elementRight);
    }

    /**
     * Выводит prompt текущей консоли
     */
    public void prompt() {
        print(P);
    }

    /**
     * @return prompt текущей консоли
     */
    public String getPrompt() {
        return P;
    }

    /**
     * Выбирает в качестве текущего сканера файловый сканер.
     * @param scanner файловый сканер
     */
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    /**
     * Выбирает в качестве текущего сканера консольный сканер.
     */
    public void selectConsoleScanner() {
        fileScanner = null;
    }

    /**
     * Возвращает текущий сканер.
     * @return сканер
     */
    public Scanner getCurrentScanner() {
        return Objects.nonNull(fileScanner) ? fileScanner : defScanner;
    }
}

