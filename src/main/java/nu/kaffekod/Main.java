package nu.kaffekod;

import nu.kaffekod.dao.impl.PersonDaoImpl;
import nu.kaffekod.db.MySQLConnection;

import java.sql.*;


public class Main {
    public static void main(String[] args) {

        Connection mySqlConnection = null;
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            mySqlConnection = MySQLConnection.getConnection();
            mySqlConnection.setAutoCommit(false); // start transaction

            // MySQL
            try {
                PersonDaoImpl personDao = new PersonDaoImpl(mySqlConnection);

                Person savedPerson1 = personDao.create(new Person("Pelle", "Påhittad")); // DONE
                System.out.println("savedPerson1 = " + savedPerson1);

                System.out.println("Operation is Done!");


            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            // an error happened
            mySqlConnection.commit(); // save both records permanently

        } catch (SQLException e) {
            try {
                mySqlConnection.rollback(); // Rollback transaction (Undo both insert queries)
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }


    }
}