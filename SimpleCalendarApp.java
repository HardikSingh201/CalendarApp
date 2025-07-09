import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SimpleCalendarApp extends JFrame {

    // Panel to hold the to-do list items
    private JPanel todoListPanel;
    // Text field for adding new to-do items
    private JTextField newTodoField;
    // List to store the to-do items (for internal management)
    private List<JCheckBox> todoItems;

    public SimpleCalendarApp() {
        // Set the title of the frame
        setTitle("My Calendar & To-Do List");
        // Set the size of the frame
        setSize(500, 600);
        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the background color of the content pane to black
        getContentPane().setBackground(Color.BLACK);
        // Use BorderLayout for the main frame layout
        setLayout(new BorderLayout(10, 10)); // Add some padding

        // Initialize the list of to-do items
        todoItems = new ArrayList<>();

        // --- Header Panel for Date, Day, Year ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK); // Header panel background is black
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Center alignment with spacing

        // Get current date information
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd"); // Day of month
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE"); // Full day name
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy"); // Year

        // Create and style Date Label
        JLabel dateLabel = new JLabel(today.format(dateFormatter));
        styleDateComponent(dateLabel);
        headerPanel.add(dateLabel);

        // Create and style Day Label
        JLabel dayLabel = new JLabel(today.format(dayFormatter));
        styleDateComponent(dayLabel);
        headerPanel.add(dayLabel);

        // Create and style Year Label
        JLabel yearLabel = new JLabel(today.format(yearFormatter));
        styleDateComponent(yearLabel);
        headerPanel.add(yearLabel);

        // Add header panel to the top of the frame
        add(headerPanel, BorderLayout.NORTH);

        // --- To-Do List Section ---
        JPanel todoSectionPanel = new JPanel();
        todoSectionPanel.setBackground(Color.BLACK); // To-do section background is black
        todoSectionPanel.setLayout(new BorderLayout(10, 10)); // Layout for input and list area

        // Input Panel for adding new tasks
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.BLACK);
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        newTodoField = new JTextField(25); // Text field for new task
        newTodoField.setFont(new Font("Arial", Font.PLAIN, 16));
        newTodoField.setForeground(Color.WHITE); // Text color white
        newTodoField.setBackground(Color.DARK_GRAY); // Background for text field
        newTodoField.setCaretColor(Color.WHITE); // Cursor color
        inputPanel.add(newTodoField);

        JButton addButton = new JButton("Add Work");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(50, 150, 50)); // Greenish add button
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false); // Remove focus border
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTodoItem(); // Call method to add a new to-do item
            }
        });
        inputPanel.add(addButton);

        todoSectionPanel.add(inputPanel, BorderLayout.NORTH);

        // Panel to display the list of to-do items
        todoListPanel = new JPanel();
        todoListPanel.setBackground(Color.BLACK);
        // Use BoxLayout to stack items vertically
        todoListPanel.setLayout(new BoxLayout(todoListPanel, BoxLayout.Y_AXIS));
        // Add some initial padding to the todoListPanel
        todoListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Wrap the todoListPanel in a JScrollPane for scrollability if many items
        JScrollPane scrollPane = new JScrollPane(todoListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border
        scrollPane.getViewport().setBackground(Color.BLACK); // Set viewport background to black

        todoSectionPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the to-do section to the center of the frame
        add(todoSectionPanel, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Helper method to style the date, day, and year components.
     * Each component is placed in a red JPanel with white text.
     * 
     * @param label The JLabel to be styled.
     */
    private void styleDateComponent(JLabel label) {
        // Create a sub-panel for each date component to give it a red background
        JPanel redBoxPanel = new JPanel();
        redBoxPanel.setBackground(Color.RED);
        redBoxPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the red box
        redBoxPanel.add(label);

        label.setForeground(Color.WHITE); // Text color white
        label.setFont(new Font("Arial", Font.BOLD, 55)); // Font for date, day, year
    }

    /**
     * Adds a new to-do item to the list.
     */
    private void addTodoItem() {
        String todoText = newTodoField.getText().trim();
        if (!todoText.isEmpty()) {
            // Create a JCheckBox for the new to-do item
            JCheckBox checkBox = new JCheckBox(todoText);
            checkBox.setForeground(Color.WHITE); // Text color white
            checkBox.setBackground(Color.BLACK); // Checkbox background black
            checkBox.setFont(new Font("Arial", Font.PLAIN, 18));
            checkBox.setFocusPainted(false); // Remove focus border for checkbox

            // Add some padding around the checkbox
            checkBox.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

            // Add the checkbox to our internal list and the UI panel
            todoItems.add(checkBox);
            todoListPanel.add(checkBox);

            // Clear the text field
            newTodoField.setText("");

            // Revalidate and repaint the panel to show the new item
            todoListPanel.revalidate();
            todoListPanel.repaint();
        } else {
            // Show a message if the text field is empty
            JOptionPane.showMessageDialog(this, "Please enter a task.", "Empty Task", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Ensure the Swing UI is created and updated on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleCalendarApp();
            }
        });
    }
}