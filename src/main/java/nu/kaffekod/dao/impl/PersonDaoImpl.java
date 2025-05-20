package nu.kaffekod.dao.impl;

import java.sql.*;
import nu.kaffekod.model.Person;
import nu.kaffekod.dao.IPersonDao;

import java.util.ArrayList;
import java.util.List;

/* optional
1. Generic interface - baseDao
 add create and findAll methods

2. mysql login from property file

3. email sender - javamailsender framework
 add email-field to person
 send email when new to-do is created
*/



public class PersonDaoImpl implements IPersonDao {
    static final String USE_DATABASE = "USE todoit";
    static final String TABLE_NAME = "person";
    static final String PERSON_ID = "person_id";
    static final String FIRST_NAME = "first_name";
    static final String LAST_NAME = "last_name";
    private Connection connection;

    public PersonDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Person create(Person person) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + FIRST_NAME + ", " + LAST_NAME + ") VALUES(?, ?)";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Affected rows" + affectedRows);

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int generatedId = resultSet.getInt(1);
                    System.out.println("generatedId = " + generatedId);
                    person.setId(generatedId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error saving person");
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try ( PreparedStatement preparedStatement = connection.prepareStatement(USE_DATABASE, Statement.RETURN_GENERATED_KEYS) ) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                persons.add(new Person(resultSet.getInt(PERSON_ID), resultSet.getString(FIRST_NAME), resultSet.getString(LAST_NAME) ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all persons");
            e.printStackTrace();
        }

        return persons;
    }

    @Override
    public List<Person> findByName(String name) {
        name = name.replaceAll("\\s+","");
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIRST_NAME + " LIKE \"%" + name + "%\" OR " + LAST_NAME + " LIKE \"%" + name + "%\"";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(USE_DATABASE, Statement.RETURN_GENERATED_KEYS) ) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while (resultSet.next()) {
                persons.add(new Person(resultSet.getInt(PERSON_ID), resultSet.getString(FIRST_NAME), resultSet.getString(LAST_NAME) ));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all persons");
            e.printStackTrace();
        }

        return persons;
    }

    @Override
    public Person update(Person person) {
        Person updatedPerson = null;
        String sqlUpdate = "UPDATE " + TABLE_NAME + " SET " + FIRST_NAME + " = ?, " + LAST_NAME + " = ? WHERE " + PERSON_ID + " = ?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate, Statement.RETURN_GENERATED_KEYS) ) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if(affectedRows > 0) {
                String sqlSelect = "SELECT * FROM " + TABLE_NAME + " WHERE " + PERSON_ID + " = " + person.getId();
                ResultSet resultSet = preparedStatement.executeQuery(sqlSelect);

                while (resultSet.next()) {
                    updatedPerson = new Person(resultSet.getInt(PERSON_ID), resultSet.getString(FIRST_NAME), resultSet.getString(LAST_NAME));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error updating person");
            e.printStackTrace();
        }

        return updatedPerson;
    }

    @Override
    public boolean deleteById(int id) {
        boolean deleted = false;
        String sqlUpdate = "DELETE FROM " + TABLE_NAME + " WHERE " + PERSON_ID + " = ?";

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
