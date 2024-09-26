package user_management.dao;

import user_management.model.Todo;

import java.util.List;

public interface TodoDAO {
    void addTodo(Todo todo);
    List<Todo> getTodoByUsername(String username);

    List<Todo> getTodosByUsername(String username);

    void updateTodo(Todo todo);
    void deleteTodo(int id);
}
