package com.geekbrains.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;
    private String login;

    public String getNickname() {
        return nickname;
    }

    public ClientHandler(Server server, Socket socket, ExecutorService service) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        service.execute(() -> {
            try {
                while (true) { //цикл аутентификации
                    String msg = in.readUTF();
                    System.out.print("Сообщение от клиента: " + msg + "\n");
                    if (msg.startsWith("/auth ")) { // /auth login1 pass1
                        String[] tokens = msg.split(" ", 3);
                        String nickFromAuthManager = server.getAuthManager().getNicknameByLoginAndPassword(tokens[1], tokens[2]);
                        login = tokens[1];
                        if (nickFromAuthManager != null) {
                            if (server.isNickBusy(nickFromAuthManager)) {
                                sendMsg("Данный пользователь уже в чате.");
                                continue;
                            }
                            nickname = nickFromAuthManager;
                            sendMsg("/authok " + nickname);
                            server.subscribe(this);
                            break;
                        } else {
                            sendMsg("Указан неверный логин/пароль.");
                        }
                    }
                }
                while (true) { //цикл общения с сервером (обмен текстовыми сообщениями и командами)
                    String msg = in.readUTF();
                    System.out.print("Сообщение от клиента: " + msg + "\n");
                    String[] tokens = msg.split(" ", 3);
                    if (msg.startsWith("/")) {
                        if (tokens[0].equals("/end")) {
                            sendMsg("/end_confirm");
                            break;
                        }

                        if (tokens[0].equals("/change_nick")) {
                            String oldNickname = nickname;
                            nickname = tokens[1];
                            sendMsg("/change_nick_ok " + nickname);
                            server.broadcastClientsList();
                            server.getAuthManager().changeNickname(nickname, login);
                            server.broadcastMsg("Пользователь " + oldNickname + " изменил имя на " + nickname, false);
                        }

                        if (tokens[0].equals("/w")) { // /w user2 hello, user2
                            server.sendPrivateMsg(this, tokens[1], tokens[2]);
                            continue;
                        }

                    } else {
                        server.broadcastMsg(nickname + ": " + msg, true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        });
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        server.unsubscribe(this);
        nickname = null;
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
