package labs.lab7.common.managers;


import labs.lab7.common.models.LabWork;
import labs.lab7.common.utility.Console;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Парсер CSV-строк.
 */
public class CSVParser {
    public static final String FILE_EXTENSION = ".csv";
    public static final String DELIMITER = ",";
    public static final int BUFFER_SIZE = 1024;
    private final String fileName;
    private final Console console;

    public CSVParser(String fileName, Console console) {
        this.fileName = fileName + FILE_EXTENSION;
        this.console = console;
    }

    /**
     * Преобразует CSV-строку в List, содержащий токены.
     * @param csvLine CSV-строка
     * @return массив токенов
     */
    public static String[] parseCSVLineToTokens(String csvLine) {

        return csvLine.split("\\s*" + DELIMITER + "\\s*");
    }

    /**
     * Преобразует коллекцию {@code LabWork} в List из CSV-строк. Отбрасывает те {@code LabWork}, которые равны null.
     * @param collection коллекция {@code LabWork}
     * @return List, содержащий CSV-строки
     */
    private List<String> parseCollectionToCSV(Collection<LabWork> collection) {
        return collection.stream()
                .filter(Objects::nonNull)
                .map(LabWork::toCSVString)
                .toList();
    }

    /**
     * Записывает коллекцию {@code LabWork} в файл в формате CSV. Отбрасывает те {@code LabWork}, которые равны null.
     * @param collection коллекция {@code LabWork}
     */
    public void writeCollection(Collection<LabWork> collection) {
        List<String> csvLines = parseCollectionToCSV(collection);
        if (csvLines.size() != collection.size()) {
            console.println("Коллекция содержит некорректные данные. Корректными оказались " +
                    csvLines.size() + " элементов из " + collection.size());
        }
        try {
            writeAllLines(csvLines);
        } catch (SecurityException e) {
            console.printError("Ошибка нарушения безопасности");
            System.exit(0);
        }  catch (IOException e) {
            console.printError("Непредвиденная ошибка при выгрузке данных");
            System.exit(0);
        }
    }

    /**
     * Преобразует CSV-строки в коллекцию {@code LabWork}. Отбрасывает некорректные строки.
     * @param csvLines List, содержащий CSV-строки
     * @return коллекция {@code LabWork}
     */
    private Collection<LabWork> parseCSVToCollection(String[] csvLines) {
        return Arrays.stream(csvLines)
                .map(LabWork::createFromCSVLine)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Считывает CSV-файл в коллекцию {@code LabWork}. Отбрасывает некорректные строки.
     * @param collection коллекция {@code LabWork}
     */
    public void readCSVToCollection(Collection<LabWork> collection) {
        try {
            String[] csvLines = readAllLines();
            Collection<LabWork> result = parseCSVToCollection(csvLines);
            collection.clear();
            collection.addAll(result);
            console.println("Парсер смог распарсить " + result.size() + "/" + csvLines.length + " элементов");
        } catch (FileNotFoundException e) {
            console.println("Загрузочный файл не найден");
        } catch (SecurityException e) {
            console.printError("Ошибка нарушения безопасности");
        } catch (IOException e) {
            console.printError("Непредвиденная ошибка при чтении файла");
        }
    }

    /**
     * Считывает все строки из файла {@code fileName} в список.
     * @return {@code List} из считанных строк
     * @throws IOException если произошла проблема при чтении
     * @throws SecurityException если произошло нарушение безопасности
     */
    private String[] readAllLines() throws IOException, SecurityException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(fileName));
        StringBuilder sb = new StringBuilder();
        byte[] b = new byte[BUFFER_SIZE];
        int size;
        while ((size = is.read(b)) >= 0) {
            for (int i = 0; i < size; i++) {
                sb.append((char) b[i]);
            }
        }
        is.close();
        return sb.toString().split(System.lineSeparator());
    }

    /**
     * Пишет все строки из переданного списка строк в файл {@code fileName}.
     * @param lines {@code List}, содержащий строки
     * @throws IOException если произошла проблема при записи
     * @throws SecurityException если произошло нарушение безопасности
     */
    private void writeAllLines(List<String> lines) throws IOException, SecurityException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName));
        for (String line : lines) {
            writer.write(line);
            writer.write(System.lineSeparator());
        }
        writer.flush();
        writer.close();
    }
}
