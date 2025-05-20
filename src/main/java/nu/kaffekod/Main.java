package nu.kaffekod;

import nu.kaffekod.dao.impl.PersonDaoImpl;
import nu.kaffekod.db.MySQLConnection;
import nu.kaffekod.model.Person;

import java.sql.*;

/* Test run with:
* java -cp .:/usr/share/java/mysql-connector-java-9.3.0.jar Main.java
* */

public class Main {
    public static void main(String[] args) {

        Connection mySqlConnection = null;
        try {
            mySqlConnection = MySQLConnection.getConnection();
            mySqlConnection.setAutoCommit(false); // start transaction

            // MySQL
            try {
                PersonDaoImpl personDao = new PersonDaoImpl(mySqlConnection);
                /*
                Person savedPerson = personDao.create(new Person("Pelle", "Tall"));
                System.out.println("savedPerson = " + savedPerson);
                System.out.println("==========");
                /* */

                /*
                System.out.println("Operation is Done! All persons so far: ");
                System.out.println(personDao.findAll());
                System.out.println("==========");
                /* */

                /*
                System.out.println("Find all named 'Stig': ");
                System.out.println(personDao.findByName("Stig"));
                System.out.println("==========");
                /* */

                /*
                System.out.println("Update");
                System.out.println(personDao.update( new Person(19, "Stigge", "Studs") ));
                System.out.println("==========");
                /* */

                /*
                System.out.println("Delete");
                System.out.println(personDao.deleteById(20));
                System.out.println("==========");
                /* */



            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            // an error happened
            mySqlConnection.commit(); // save both records permanently

        } catch (SQLException e) {
            try {
                mySqlConnection.rollback(); // Rollback transaction (Undo both insert queries)
                System.out.println(e.getMessage());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }


    }
}