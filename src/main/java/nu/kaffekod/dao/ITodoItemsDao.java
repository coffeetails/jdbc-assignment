package nu.kaffekod.dao;

import nu.kaffekod.model.Person;
import nu.kaffekod.model.Todo;

import java.util.List;

public interface ITodoItemsDao {
    public Todo create(Todo todo);
    public List<Todo> findAll();
    public Todo findById(int id);
    public List<Todo> findByDoneStatus(boolean done);
    public List<Todo> findByAssignee(int personId);
    public List<Todo> findByAssignee(Person person);
    public List<Todo> findByUnassignedTodoItems();
    public Todo update(Todo todo);
    public boolean deleteById(int id);

}
