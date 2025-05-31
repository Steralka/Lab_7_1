package labs.lab7.client.utility;

import labs.lab7.common.exceptions.ServerConnectionException;
import labs.lab7.common.network.requests.Request;
import labs.lab7.common.network.responses.Response;
import labs.lab7.common.utility.Console;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private final String host;
    private final int port;
    private final int reconnectionTimeout;
    private final int maxReconnectionAttempts;
    private final Console console;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, Console console) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.console = console;
    }

    public boolean connect() {
        int reconnectionAttempts = 0;

        while (true) {
            try {
                socket = new Socket(host, port);
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                console.println("Подключение к серверу успешно.");
                return true;
            } catch (UnknownHostException e) {
                console.printError("Некорректный адрес сервера: " + host);
                return false;
            } catch (IOException e) {
                reconnectionAttempts++;
                console.printError("Ошибка соединения с сервером. Попытка " + reconnectionAttempts);
                if (reconnectionAttempts >= maxReconnectionAttempts) {
                    console.printError("Превышено максимальное количество попыток подключения.");
                    return false;
                }
                try {
                    Thread.sleep(reconnectionTimeout);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public Response sendRequest(Request request) throws ServerConnectionException {
        if (socket == null || socket.isClosed()) {
            throw new ServerConnectionException("Нет подключения к серверу");
        }

        try {
            out.writeObject(request);
            out.flush();

            Object obj = in.readObject();
            if (obj instanceof Response response) {
                return response;
            } else {
                throw new ServerConnectionException("Получен некорректный ответ от сервера");
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new ServerConnectionException("Ошибка при обмене данными с сервером: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            if (socket != null) socket.close();
            if (out != null) out.close();
            if (in != null) in.close();
        } catch (IOException e) {
            console.printError("Ошибка при отключении от сервера: " + e.getMessage());
        }
    }
}
