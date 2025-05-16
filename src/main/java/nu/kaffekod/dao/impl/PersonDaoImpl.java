package nu.kaffekod.dao.impl;

import java.sql.*;
import nu.kaffekod.Person;
import nu.kaffekod.dao.IPersonDao;

import java.util.List;

public class PersonDaoImpl implements IPersonDao {

    private Connection connection;

    public PersonDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Person create(Person person) {
        String sql = "INSERT INTO person (firstname, lastname) VALUES(?, ?, ?)";
        try (
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
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
        return List.of();
    }

    @Override
    public List<Person> findByName(String name) {
        return List.of();
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
