package com.geekbrains.server;

import java.sql.SQLException;

public class BasicAuthManager implements AuthManager {

    private DBManager dbManager = new DBManager();

    public BasicAuthManager() {
        try {
            dbManager.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        return dbManager.getNickname(login, password);
    }

    @Override
    public void close() {
        dbManager.disconnect();
    }

    @Override
    public void changeNickname(String newNickname, String login) {
        dbManager.changeNickname(login, newNickname);
    }
}
