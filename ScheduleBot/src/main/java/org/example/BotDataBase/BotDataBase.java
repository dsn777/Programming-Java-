package org.example.BotDataBase;

import java.sql.*;

public class BotDataBase {
    private final static String URL = "jdbc:mysql://localhost:3306/MyDataBase";
    private final static String USER = "root";
    private final static String PASSWORD = "user";

    public static Connection connection;
    public static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Подключение к базе данных успешно установлено!");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных:");
            e.printStackTrace();
        }
    }
    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Write(Long chatId, String userName,String firstName, String lastName) throws SQLException {

        String query = "Select * from bot_users WHERE ChatId = " + chatId.toString();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next())
            return;

        query = "INSERT INTO bot_users (" +
                "ChatId, " +
                "UserName," +
                "FirstName, " +
                "LastName) " +
                "VALUES(" +
                chatId + "," +
                "'" + userName + "'" + "," +
                "'" + firstName + "'" + "," +
                "'" + lastName + "'" + ");";
        statement.executeUpdate(query);
    }
}