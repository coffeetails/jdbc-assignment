package nu.kaffekod.dao;

import nu.kaffekod.model.Person;

import java.util.List;

public interface IPersonDao {
    public Person create(Person person);
    public List<Person> findAll();
    public Person findById(int id);
    public List<Person> findByName(String name);
    public Person update(Person person);
    public boolean deleteById(int id);

}
