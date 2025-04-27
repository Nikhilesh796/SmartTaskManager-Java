import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private int taskIdCounter = 1;
    private final String FILE_NAME = "tasks.txt";

    public TaskManager() {
        loadTasksFromFile();
    }

    public void addTask(String title, String desc, String date, String priority) {
        Task task = new Task(taskIdCounter++, title, desc, date, priority);
        tasks.add(task);
        System.out.println("Task added successfully!");
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void markComplete(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(true);
                System.out.println("Task marked as completed.");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    public void deleteTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
        System.out.println("Task deleted if it existed.");
    }

    public void saveTasksToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                pw.println(task.getId() + ";" + task.getTitle() + ";" + task.getDescription() + ";" +
                        task.getDueDate() + ";" + task.getPriority() + ";" + task.isCompleted());
            }
            System.out.println("Tasks saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void loadTasksFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 6) {
                    Task task = new Task(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]);
                    if (Boolean.parseBoolean(parts[5])) {
                        task.setCompleted(true);
                    }
                    tasks.add(task);
                    taskIdCounter = Math.max(taskIdCounter, task.getId() + 1);
                }
            }
        } catch (IOException e) {
            // No tasks.txt yet? That's fine.
        }
    }
}
