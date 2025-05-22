package nu.kaffekod;

import nu.kaffekod.dao.impl.PersonDaoImpl;
import nu.kaffekod.dao.impl.TodoItemDaoImpl;
import nu.kaffekod.db.MySQLConnection;
import nu.kaffekod.model.Person;
import nu.kaffekod.model.TodoItem;

import java.sql.*;
import java.time.LocalDate;

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
                System.out.println("All persons so far: ");
                System.out.println(personDao.findAll());
                System.out.println("==========");
                /* */

                /*
                System.out.println("Find by id 17: ");
                System.out.println(personDao.findById(17));
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

                TodoItemDaoImpl todoItemDao = new TodoItemDaoImpl(mySqlConnection);
                /*
                TodoItem saveTodoOne = todoItemDao.create(new TodoItem("Wash clothes"));
                TodoItem saveTodoTwo = todoItemDao.create(new TodoItem("Clean floors", "Don't mop the walls", LocalDate.now().minusDays(100), personDao.findById(17)));
                TodoItem saveTodoThree = todoItemDao.create(new TodoItem("Scrub the walls", "Don't drip on the floors", LocalDate.now().plusDays(100), personDao.findById(17)));
                System.out.println("==========");
                /* */

                /*
                System.out.println("All todos so far: ");
                for(TodoItem todoItem : todoItemDao.findAll()) {
                    System.out.println(todoItem);
                }
                System.out.println("==========");
                /* */

                /*
                System.out.println("Find todoitem by id 8: ");
                System.out.println(todoItemDao.findById(8));
                System.out.println("==========");
                /* */

                /*
                System.out.println("Find toditems by done status: ");
                for(TodoItem todoItem : todoItemDao.findByDoneStatus(true)) {
                    System.out.println(todoItem);
                }
                System.out.println("==========");
                /* */

                /*
                System.out.println("Find todoitems by assignee id 17: ");
                for(TodoItem todoItem : todoItemDao.findByAssignee(17)) {
                    System.out.println(todoItem);
                }
                System.out.println("==========");
                /* */

                /*
                System.out.println("Find todoitems by assignee Henry: ");
                for(TodoItem todoItem : todoItemDao.findByAssignee(new Person(17, "Henry", "Stigsson"))) {
                    System.out.println(todoItem);
                }
                System.out.println("==========");
                /* */

                /*
                System.out.println("Find unassigned todoitems: ");
                for(TodoItem todoItem : todoItemDao.findByUnassignedTodoItems()) {
                    System.out.println(todoItem);
                }
                System.out.println("==========");
                /* */

                /*
                System.out.println("Update todoitem: ");
                System.out.println(todoItemDao.update(new TodoItem(
                        7,
                        "Clean floors",
                        "Don't mop the walls",
                        LocalDate.of(2025,2,10),
                        true,
                        null)));
                System.out.println("==========");
                /* */

                /*
                System.out.println("Delete todoitem: ");
                System.out.println(todoItemDao.deleteById(4));
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