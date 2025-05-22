package nu.kaffekod.dao;

import nu.kaffekod.model.Person;
import nu.kaffekod.model.TodoItem;

import java.util.List;

public interface ITodoItemsDao {
    public TodoItem create(TodoItem todoItem);
    public List<TodoItem> findAll();
    public TodoItem findById(int id);
    public List<TodoItem> findByDoneStatus(boolean done);
    public List<TodoItem> findByAssignee(int personId);
    public List<TodoItem> findByAssignee(Person person);
    public List<TodoItem> findByUnassignedTodoItems();
    public TodoItem update(TodoItem todoItem);
    public boolean deleteById(int id);

}
