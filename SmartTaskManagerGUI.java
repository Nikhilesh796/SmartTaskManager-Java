import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SmartTaskManagerGUI extends JFrame {
    private TaskManager taskManager;
    private JTable table;
    private DefaultTableModel tableModel;

    public SmartTaskManagerGUI() {
        taskManager = new TaskManager();
        setTitle("Smart Task Manager");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Title", "Description", "Due Date", "Priority", "Status" }, 0);
        table = new JTable(tableModel);
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);

        JButton addButton = new JButton("Add Task");
        JButton markCompleteButton = new JButton("Mark Complete");
        JButton deleteButton = new JButton("Delete Task");
        JButton saveButton = new JButton("Save Tasks");

        addButton.addActionListener(e -> showAddTaskDialog());
        markCompleteButton.addActionListener(e -> markSelectedTaskComplete());
        deleteButton.addActionListener(e -> deleteSelectedTask());
        saveButton.addActionListener(e -> taskManager.saveTasksToFile());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(markCompleteButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(saveButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // clear table
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            tableModel.addRow(new Object[] {
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.getPriority(),
                    task.isCompleted() ? "Completed" : "Pending"
            });
        }
    }

    private void showAddTaskDialog() {
        JTextField titleField = new JTextField(10);
        JTextField descField = new JTextField(10);
        JTextField dueDateField = new JTextField(10);
        String[] priorities = { "High", "Medium", "Low" };
        JComboBox<String> priorityBox = new JComboBox<>(priorities);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        panel.add(dueDateField);
        panel.add(new JLabel("Priority:"));
        panel.add(priorityBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Task", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            taskManager.addTask(titleField.getText(), descField.getText(), dueDateField.getText(),
                    (String) priorityBox.getSelectedItem());
            refreshTable();
        }
    }

    private void markSelectedTaskComplete() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int taskId = (int) tableModel.getValueAt(selectedRow, 0);
            taskManager.markComplete(taskId);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark complete.");
        }
    }

    private void deleteSelectedTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int taskId = (int) tableModel.getValueAt(selectedRow, 0);
            taskManager.deleteTask(taskId);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SmartTaskManagerGUI gui = new SmartTaskManagerGUI();
            gui.setVisible(true);
        });
    }
}
