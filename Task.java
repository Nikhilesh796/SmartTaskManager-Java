import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private boolean isCompleted;

    public Task(int id, String title, String description, String dueDate, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public String getPriority() { return priority; }
    public boolean isCompleted() { return isCompleted; }

    public void setCompleted(boolean status) { this.isCompleted = status; }

    @Override
    public String toString() {
        return id + " | " + title + " | " + description + " | " + dueDate + " | " + priority + " | " +
               (isCompleted ? "Completed" : "Pending");
    }
}
