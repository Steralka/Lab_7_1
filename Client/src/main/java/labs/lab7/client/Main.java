package labs.lab7.client;

import labs.lab7.client.utility.Client;
import labs.lab7.client.utility.Runner;
import labs.lab7.common.utility.Console;
import labs.lab7.common.utility.StandardConsole;

public class Main {
    public static void main(String[] args) {
        Console console = new StandardConsole();

        try {
            if (args.length != 2) throw new IllegalArgumentException(
                    "Передайте хост и порт в аргументы командной строки в формате <host> <port>"
            );

            String host = args[0];
            int port = Integer.parseInt(args[1]);

            if (port < 1 || port > 65535) throw new IllegalArgumentException("Невалидный порт");

            var client = new Client(host, port, 5000, 5, console);
            if (!client.connect()) return;
            var runner = new Runner(console, client);
            runner.interactiveMode();
        } catch (IllegalArgumentException e) {
            console.printError(e.getMessage());
        }
    }
}