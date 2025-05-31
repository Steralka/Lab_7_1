package labs.lab7.server.utility;

import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.utility.Console;
import labs.lab7.server.exceptions.OpenServerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final RequestHandler requestHandler;
    private final Console console;
    private final ExecutorService cachedPoolRequest;
    private ConnectionHandler connectionHandler;

    public Server(int port, RequestHandler requestHandler, Console console) {
        this.port = port;
        this.requestHandler = requestHandler;
        this.console = console;
        this.cachedPoolRequest = Executors.newCachedThreadPool();
    }

    public void run() throws OpenServerException, ServerConnectionException {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            console.println("Сервер запущен на порту " + port);

            connectionHandler = new ConnectionHandler(requestHandler, console, serverSocket);
            cachedPoolRequest.submit(connectionHandler);

            while (!cachedPoolRequest.isShutdown()) {
                Thread.sleep(1000);
            }

            close();
            serverSocket.close();
            console.println("Сервер остановлен");
        } catch (IllegalArgumentException e) {
            throw new OpenServerException("Невалидный порт");
        } catch (IOException | InterruptedException e) {
            console.printError("Ошибка сервера: " + e.getMessage());
        }
    }

    public void close() {
        if (Objects.nonNull(connectionHandler)) {
            connectionHandler.stop();
            connectionHandler = null;
        }
        cachedPoolRequest.shutdown();
    }
}
