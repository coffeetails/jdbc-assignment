package nu.kaffekod.db;

import java.sql.*;
import java.util.Scanner;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/todoit?useSSL=false&serverTimezone=UTC";

    public static Connection getConnection() throws SQLException {
        System.out.println("Log in to mysql");
        /* */
        Scanner scanner = new Scanner(System.in);
        System.out.print("DB username: ");
        String dbUser = scanner.nextLine();
        System.out.print("DB password: ");
        String dbPassword = scanner.nextLine();
        /* */

        //Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, dbUser, dbPassword);
    }

}
