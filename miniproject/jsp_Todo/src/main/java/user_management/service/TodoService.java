package user_management.service;

import user_management.model.Todo;
import java.util.List;

public interface TodoService {
    void addTodo(Todo todo);
    List<Todo> getTodosByUsername(String username);
    void updateTodo(Todo todo);
    void deleteTodo(int id);
}