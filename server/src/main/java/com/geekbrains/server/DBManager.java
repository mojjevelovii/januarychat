package com.geekbrains.server;

import java.sql.*;

public class DBManager {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement addUserPS;
    private PreparedStatement deleteUserPS;
    private PreparedStatement changeNicknamePS;
    private PreparedStatement getNicknamePS;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:dbauth.db");
        stmt = connection.createStatement();
        prepareStatements();

    }

    public void prepareStatements() throws SQLException {
        addUserPS = connection.prepareStatement("INSERT INTO users (login, password, nickname) VALUES (?,?,?);");
        deleteUserPS = connection.prepareStatement("DELETE FROM users WHERE login = ?;");
        changeNicknamePS = connection.prepareStatement("UPDATE users SET nickname = ? WHERE login = ?;");
        getNicknamePS = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");
    }

    public void disconnect() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean addUser(String login, String password, String nickname) {
        try {
            addUserPS.setString(1, login);
            addUserPS.setString(2, password);
            addUserPS.setString(3, nickname);
            int count = addUserPS.executeUpdate();
            return count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String login, String password, String nickname) {
        try {
            deleteUserPS.setString(1, login);
            int count = deleteUserPS.executeUpdate();
            return count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeNickname(String login, String newNickname) {
        try {
            changeNicknamePS.setString(1, newNickname);
            changeNicknamePS.setString(2, login);
            int count = changeNicknamePS.executeUpdate();
            return count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getNickname(String login, String password) {
        try {
            getNicknamePS.setString(1, login);
            getNicknamePS.setString(2, password);
            ResultSet resultSet = getNicknamePS.executeQuery();
            return resultSet.getString("nickname");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dropAndCreateTable() {
        try {
            stmt.executeUpdate("DROP TABLE IF EXISTS users;");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users(login STRING UNIQUE, password STRING NOT NULL, nickname STRING NOT NULL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
