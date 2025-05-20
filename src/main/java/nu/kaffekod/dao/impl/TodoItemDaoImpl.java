package nu.kaffekod.dao.impl;

import nu.kaffekod.model.Person;
import nu.kaffekod.model.Todo;
import nu.kaffekod.dao.ITodoItemsDao;

import java.util.List;

public class TodoItemDaoImpl implements ITodoItemsDao {

    @Override
    public Todo create(Todo todo) {
        return null;
    }

    @Override
    public List<Todo> findAll() {
        return List.of();
    }

    @Override
    public Todo findById(int id) {
        return null;
    }

    @Override
    public List<Todo> findByDoneStatus(boolean done) {
        return List.of();
    }

    @Override
    public List<Todo> findByAssignee(int personId) {
        return List.of();
    }

    @Override
    public List<Todo> findByAssignee(Person person) {
        return List.of();
    }

    @Override
    public List<Todo> findByUnassignedTodoItems() {
        return List.of();
    }

    @Override
    public Todo update(Todo todo) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
