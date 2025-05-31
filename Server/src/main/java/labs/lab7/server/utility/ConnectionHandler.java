package labs.lab7.server.utility;

import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.Response;
import labs.lab7.common.utility.Console;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ConnectionHandler implements Runnable {

    private static final int POOL_SIZE = 4;

    private final RequestHandler requestHandler;
    private final Console console;
    private final ServerSocket serverSocket;
    private final ExecutorService fixedPoolResponse;
    private volatile boolean running = true;

    public ConnectionHandler(RequestHandler requestHandler, Console console, ServerSocket serverSocket) {
        this.requestHandler = requestHandler;
        this.console = console;
        this.serverSocket = serverSocket;
        this.fixedPoolResponse = Executors.newFixedThreadPool(POOL_SIZE);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                console.println("Новое соединение: " + clientSocket.getInetAddress());

                fixedPoolResponse.submit(() -> handleClient(clientSocket));

            } catch (IOException e) {
                if (running) {
                    console.printError("Ошибка подключения: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            outputStream.flush();

            while (!clientSocket.isClosed()) {
                Request request = (Request) inputStream.readObject();
                console.println("Получен запрос: " + request.getClass().getSimpleName());

                Future<Response> task = fixedPoolResponse.submit(requestHandler.handleAsync(request));
                Response response = task.get();

                outputStream.writeObject(response);
                outputStream.flush();
            }

        } catch (EOFException e) {
            console.printError("Клиент отключился");
        } catch (IOException | ClassNotFoundException | InterruptedException | ExecutionException e) {
            console.printError("Ошибка при обработке клиента: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }

    public void stop() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            console.printError("Ошибка при остановке сервера: " + e.getMessage());
        }
        fixedPoolResponse.shutdown();
        console.println("Сервер остановлен");
    }
}
