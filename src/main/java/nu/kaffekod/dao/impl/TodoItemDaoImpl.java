package nu.kaffekod.dao.impl;

import java.sql.*;
import nu.kaffekod.model.Person;
import nu.kaffekod.model.TodoItem;
import nu.kaffekod.dao.ITodoItemsDao;

import java.util.ArrayList;
import java.util.List;


public class TodoItemDaoImpl implements ITodoItemsDao {
    static final String USE_DATABASE = "USE todoit";
    static final String TABLE_NAME = "todo_item";
    static final String TODO_ID = "todo_id";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String DEADLINE = "deadline";
    static final String DONE = "done";
    static final String ASSIGNEE_ID = "assignee_id";
    private Connection connection;

    public TodoItemDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TodoItem create(TodoItem todoItem) {
        String sql = "INSERT INTO " + TABLE_NAME +
                " (" + TODO_ID + ", " + TITLE + ", " + DESCRIPTION + ", " + DEADLINE + ", " + DONE + ", " + ASSIGNEE_ID + ") " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setInt(1, todoItem.getId());
            preparedStatement.setString(2, todoItem.getTitle());
            preparedStatement.setString(3, todoItem.getDescription());
            preparedStatement.setDate(4, todoItem.getDeadline() == null ? null : Date.valueOf(todoItem.getDeadline()));
            preparedStatement.setBoolean(5, todoItem.isDone());
            if(todoItem.getAssignee() == null) {
                preparedStatement.setNull(6, Types.INTEGER);
            } else {
                preparedStatement.setInt(6, todoItem.getAssignee().getId());
            }

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Affected rows" + affectedRows);

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int generatedId = resultSet.getInt(1);
                    System.out.println("generatedId = " + generatedId);
                    todoItem.setId(generatedId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error saving todo item");
            e.printStackTrace();
        }
        return todoItem;
    }

    @Override
    public List<TodoItem> findAll() {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try ( PreparedStatement preparedStatement = connection.prepareStatement(USE_DATABASE, Statement.RETURN_GENERATED_KEYS) ) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            PersonDaoImpl personDao = new PersonDaoImpl(connection);
            while (resultSet.next()) {
                todoItems.add(new TodoItem(
                        resultSet.getInt(TODO_ID),
                        resultSet.getString(TITLE),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getDate(DEADLINE) == null ? null : resultSet.getDate(DEADLINE).toLocalDate(),
                        resultSet.getBoolean(DONE),
                        personDao.findById(resultSet.getInt(ASSIGNEE_ID))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all todo items");
            e.printStackTrace();
        }

        return todoItems;
    }

    @Override
    public TodoItem findById(int id) {
        TodoItem foundTodoItem = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + TODO_ID + " = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            PersonDaoImpl personDao = new PersonDaoImpl(connection);
            while (resultSet.next()) {
                foundTodoItem = new TodoItem(
                        resultSet.getInt(TODO_ID),
                        resultSet.getString(TITLE),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getDate(DEADLINE) == null ? null : resultSet.getDate(DEADLINE).toLocalDate(),
                        resultSet.getBoolean(DONE),
                        personDao.findById(resultSet.getInt(ASSIGNEE_ID))
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching todo item");
            e.printStackTrace();
        }

        return foundTodoItem;
    }

    @Override
    public List<TodoItem> findByDoneStatus(boolean done) {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + DONE + " = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setBoolean(1, done);
            ResultSet resultSet = preparedStatement.executeQuery();

            PersonDaoImpl personDao = new PersonDaoImpl(connection);
            while (resultSet.next()) {
                todoItems.add(new TodoItem(
                        resultSet.getInt(TODO_ID),
                        resultSet.getString(TITLE),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getDate(DEADLINE) == null ? null : resultSet.getDate(DEADLINE).toLocalDate(),
                        resultSet.getBoolean(DONE),
                        personDao.findById(resultSet.getInt(ASSIGNEE_ID))
                ));
            }

        } catch (SQLException e) {
            if(done) {
                System.out.println("Error fetching all finished todo items");
            } else {
                System.out.println("Error fetching all unfinished todo items");
            }
            e.printStackTrace();
        }

        return todoItems;
    }

    @Override
    public List<TodoItem> findByAssignee(int personId) {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ASSIGNEE_ID + " = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();

            PersonDaoImpl personDao = new PersonDaoImpl(connection);
            while (resultSet.next()) {
                todoItems.add(new TodoItem(
                        resultSet.getInt(TODO_ID),
                        resultSet.getString(TITLE),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getDate(DEADLINE) == null ? null : resultSet.getDate(DEADLINE).toLocalDate(),
                        resultSet.getBoolean(DONE),
                        personDao.findById(resultSet.getInt(ASSIGNEE_ID))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all todo items by assignee");
            e.printStackTrace();
        }

        return todoItems;
    }

    @Override
    public List<TodoItem> findByAssignee(Person person) {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ASSIGNEE_ID + " = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setInt(1, person.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            PersonDaoImpl personDao = new PersonDaoImpl(connection);
            while (resultSet.next()) {
                todoItems.add(new TodoItem(
                        resultSet.getInt(TODO_ID),
                        resultSet.getString(TITLE),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getDate(DEADLINE) == null ? null : resultSet.getDate(DEADLINE).toLocalDate(),
                        resultSet.getBoolean(DONE),
                        personDao.findById(resultSet.getInt(ASSIGNEE_ID))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all todo items");
            e.printStackTrace();
        }

        return todoItems;
    }

    @Override
    public List<TodoItem> findByUnassignedTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ASSIGNEE_ID + " IS null";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            PersonDaoImpl personDao = new PersonDaoImpl(connection);
            while (resultSet.next()) {
                todoItems.add(new TodoItem(
                        resultSet.getInt(TODO_ID),
                        resultSet.getString(TITLE),
                        resultSet.getString(DESCRIPTION),
                        resultSet.getDate(DEADLINE) == null ? null : resultSet.getDate(DEADLINE).toLocalDate(),
                        resultSet.getBoolean(DONE),
                        personDao.findById(resultSet.getInt(ASSIGNEE_ID))
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all todo items");
            e.printStackTrace();
        }

        return todoItems;
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        String sql = "UPDATE " + TABLE_NAME +
                " SET " +
                    TODO_ID + " = ?, " +
                    TITLE + " = ?, " +
                    DESCRIPTION + " = ?, " +
                    DEADLINE + " = ?, " +
                    DONE + " = ?, " +
                    ASSIGNEE_ID + " = ? " +
                "WHERE " + TODO_ID + " = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setInt(1, todoItem.getId());
            preparedStatement.setString(2, todoItem.getTitle());
            preparedStatement.setString(3, todoItem.getDescription());
            preparedStatement.setDate(4, todoItem.getDeadline() == null ? null : Date.valueOf(todoItem.getDeadline()));
            preparedStatement.setBoolean(5, todoItem.isDone());
            if(todoItem.getAssignee() == null) {
                preparedStatement.setNull(6, Types.INTEGER);
            } else {
                preparedStatement.setInt(6, todoItem.getAssignee().getId());
            }
            preparedStatement.setInt(7, todoItem.getId());


            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Affected rows" + affectedRows);

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int generatedId = resultSet.getInt(1);
                    System.out.println("generatedId = " + generatedId);
                    todoItem.setId(generatedId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error updating todo item");
            e.printStackTrace();
        }
        return todoItem;
    }

    @Override
    public boolean deleteById(int id) {
        boolean deleted = false;
        String sqlUpdate = "DELETE FROM " + TABLE_NAME + " WHERE " + TODO_ID + " = ?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0) {
                deleted = true;
            }

        } catch (SQLException e) {
            System.out.println("Error updating person");
            e.printStackTrace();
        }

        return deleted;
    }
}
