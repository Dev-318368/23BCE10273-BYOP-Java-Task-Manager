import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskManager extends JFrame {

    // GUI Components
    private JTextField taskField;
    private DefaultListModel<String> taskModel;
    private JList<String> taskList;

    // Constructor
    public TaskManager() {

        // Window settings
        setTitle("Task Manager");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout());

        // Create components
        taskField = new JTextField();
        taskField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Task");

        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);

        JScrollPane scrollPane = new JScrollPane(taskList);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        // Add components to frame
        add(taskField, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load existing tasks from database
        loadTasks();

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        setVisible(true);
    }

    // Method to add task
    private void addTask() {

        String task = taskField.getText().trim();

        if (!task.isEmpty()) {

            TaskDatabase.insertTask(task);
            taskModel.addElement(task);
            taskField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a task.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

        }
    }

    // Method to delete selected task
    private void deleteTask() {

        int selectedIndex = taskList.getSelectedIndex();

        if (selectedIndex != -1) {

            String task = taskModel.getElementAt(selectedIndex);

            TaskDatabase.deleteTask(task);
            taskModel.remove(selectedIndex);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a task to delete.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

        }
    }

    // Method to load tasks from database
    private void loadTasks() {

        for (String task : TaskDatabase.getTasks()) {

            taskModel.addElement(task);

        }
    }

    // Main method
    public static void main(String[] args) {

        // Run GUI safely
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TaskManager();
            }
        });

    }
}