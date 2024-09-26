package user_management.model;

public class Todo {
    private int id;
    private String username;
    private String task;
    private boolean completed;

    public Todo() {}

    public Todo(int id, String username, String task, boolean completed) {
        this.id = id;
        this.username = username;
        this.task = task;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
