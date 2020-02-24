package com.geekbrains.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private AuthManager authManager;
    private List<ClientHandler> clients;

    private final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public AuthManager getAuthManager() {
        return authManager;
    }

    public Server(int port) {

        clients = new ArrayList<>();
        try {
            authManager = new BasicAuthManager();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.throwing(Level.ERROR, e);
            throw new RuntimeException(e);
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Сервер запущен. Ожидаем подключения клиентов...");
            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.info("Клиент подключился.");
                new ClientHandler(this, socket);

            }
        } catch (IOException e) {
            LOGGER.throwing(Level.ERROR, e);
            e.printStackTrace();
        } finally {
            authManager.close();
        }

    }

    public void broadcastMsg(String msg, boolean withDateTime) {
        if (withDateTime) {
            msg = String.format("[%s] %s", LocalDateTime.now().format(DTF), msg);
        }

        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public void broadcastClientsList() {
        StringBuilder stringBuilder = new StringBuilder("/clients_list ");
        for (ClientHandler o : clients) {
            stringBuilder.append(o.getNickname()).append(" ");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        String out = stringBuilder.toString();
        broadcastMsg(out, false);
    }

    public void sendPrivateMsg(ClientHandler sender, String receiverNickname, String msg) {
        if (sender.getNickname().equals(receiverNickname)) {
            sender.sendMsg("Нельзя посылать личное сообщение самому себе.");
            return;
        }
        for (ClientHandler o : clients) {
            if (o.getNickname().equals(receiverNickname)) {
                o.sendMsg("From " + sender.getNickname() + ": " + msg);
                sender.sendMsg("To " + receiverNickname + ": " + msg);
                return;
            }
        }
        sender.sendMsg(receiverNickname + " не в сети.");
    }


    public boolean isNickBusy(String nickname) {
        for (ClientHandler o : clients) {
            if (o.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        broadcastMsg(clientHandler.getNickname() + " зашел в чат.", false);
        clients.add(clientHandler);
        broadcastClientsList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMsg(clientHandler.getNickname() + " вышел из чата.", false);
        broadcastClientsList();
    }
}
