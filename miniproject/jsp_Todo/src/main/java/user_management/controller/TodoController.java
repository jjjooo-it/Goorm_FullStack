package user_management.controller;

import user_management.model.Todo;
import user_management.service.TodoService;
import user_management.service.TodoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet("/todos")
public class TodoController extends HttpServlet {
    private TodoService todoService = new TodoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        String username = request.getParameter("username");

        if (username != null) {
            username = URLDecoder.decode(username, "UTF-8");
        }

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                todoService.deleteTodo(id);
                response.sendRedirect(request.getContextPath() + "/todos?username=" + URLEncoder.encode(username, "UTF-8"));
                break;
            case "edit":
                id = Integer.parseInt(request.getParameter("id"));
                Todo todo = todoService.getTodosByUsername(username).stream().filter(t -> t.getId() == id).findFirst().orElse(null);
                request.setAttribute("todo", todo);
                request.getRequestDispatcher("/WEB-INF/views/editTodo.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("todos", todoService.getTodosByUsername(username));
                request.getRequestDispatcher("/WEB-INF/views/todos.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String task = request.getParameter("task");
        boolean completed = request.getParameter("completed") != null;

        if (username != null) {
            username = URLDecoder.decode(username, "UTF-8");
        }

        switch (action) {
            case "add":
                if (username != null && !username.isEmpty()) {
                    Todo newTodo = new Todo();
                    newTodo.setUsername(username);
                    newTodo.setTask(task);
                    newTodo.setCompleted(completed);
                    todoService.addTodo(newTodo);
                }
                break;
            case "update":
                int id = Integer.parseInt(request.getParameter("id"));
                Todo todo = new Todo();
                todo.setId(id);
                todo.setUsername(username);
                todo.setTask(task);
                todo.setCompleted(completed);
                todoService.updateTodo(todo);
                break;
            case "toggle":
                id = Integer.parseInt(request.getParameter("id"));
                todo = todoService.getTodosByUsername(username).stream().filter(t ->t.getId() == id).findFirst().orElse(null);
                if (todo != null) {
                    todo.setCompleted(!todo.isCompleted());
                    todoService.updateTodo(todo);
                }
                break;
        }

        response.sendRedirect(request.getContextPath() + "/todos?username=" + URLEncoder.encode(username, "UTF-8"));
    }
}