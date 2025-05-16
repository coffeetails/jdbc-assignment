package nu.kaffekod.db;

import java.sql.*;
import java.util.Scanner;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/todoit";

    public static Connection getConnection() throws SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        System.out.println("Log in to mysql");
        Scanner scanner = new Scanner(System.in);
        System.out.print("DB username: ");
        String dbUser = scanner.nextLine();
        System.out.print("DB password: ");
        String dbPassword = scanner.nextLine();

        return DriverManager.getConnection(URL, dbUser, dbPassword);
    }

}
