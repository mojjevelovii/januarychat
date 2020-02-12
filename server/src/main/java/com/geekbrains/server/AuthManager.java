package com.geekbrains.server;

public interface AuthManager {
    String getNicknameByLoginAndPassword(String login, String password);
    void close();
    void changeNickname(String newNickname, String login);

}
