package user_management.dao;

import user_management.model.Todo;
import user_management.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoDAOImpl implements TodoDAO {
    @Override
    public void addTodo(Todo todo) {
        String sql = "INSERT INTO todos (username, task, completed) VALUES (?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, todo.getUsername());
            statement.setString(2, todo.getTask());
            statement.setBoolean(3, todo.isCompleted());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Todo> getTodoByUsername(String username) {
        return Collections.emptyList();
    }

    @Override
    public List<Todo> getTodosByUsername(String username) {
        String sql = "SELECT * FROM todos WHERE username = ?";
        List<Todo> todos = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Todo todo = new Todo(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("task"),
                            resultSet.getBoolean("completed")
                    );
                    todos.add(todo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    @Override
    public void updateTodo(Todo todo) {
        String sql = "UPDATE todos SET task = ?, completed = ? WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, todo.getTask());
            statement.setBoolean(2, todo.isCompleted());
            statement.setInt(3, todo.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTodo(int id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}