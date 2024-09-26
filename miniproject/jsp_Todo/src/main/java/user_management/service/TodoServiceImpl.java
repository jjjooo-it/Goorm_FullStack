package user_management.service;

import user_management.dao.TodoDAO;
import user_management.dao.TodoDAOImpl;
import user_management.model.Todo;
import java.util.List;

public class TodoServiceImpl implements TodoService {
    private TodoDAO todoDAO = new TodoDAOImpl();

    @Override
    public void addTodo(Todo todo) {
        todoDAO.addTodo(todo);
    }

    @Override
    public List<Todo> getTodosByUsername(String username) {
        return todoDAO.getTodosByUsername(username);
    }

    @Override
    public void updateTodo(Todo todo) {
        todoDAO.updateTodo(todo);
    }

    @Override
    public void deleteTodo(int id) {
        todoDAO.deleteTodo(id);
    }
}