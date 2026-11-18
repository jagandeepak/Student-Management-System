
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementSystem extends JFrame {

    JTextField nameField, emailField, deptField, ageField, searchField;

    JTable table;
    DefaultTableModel model;

    ArrayList<Student> students = new ArrayList<>();

    int nextId = 1;

    public StudentManagementSystem() {

        setTitle("Student Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("STUDENT MANAGEMENT SYSTEM",
                SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        mainPanel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        nameField = new JTextField();
        emailField = new JTextField();
        deptField = new JTextField();
        ageField = new JTextField();

        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Department:"));
        formPanel.add(deptField);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        JButton addButton = new JButton("Add Student");
        JButton updateButton = new JButton("Update Student");
        JButton deleteButton = new JButton("Delete Student");
        JButton clearButton = new JButton("Clear Fields");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.CENTER);

        model = new DefaultTableModel(
                new String[]{"ID", "Name", "Email", "Department", "Age"}, 0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search by name:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        bottomPanel.add(searchPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        addButton.addActionListener(e -> addStudent());

        updateButton.addActionListener(e -> updateStudent());

        deleteButton.addActionListener(e -> deleteStudent());

        clearButton.addActionListener(e -> clearFields());

        searchButton.addActionListener(e -> searchStudent());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedStudent();
            }
        });
    }

    void addStudent() {

        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String dept = deptField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());

            if (name.isEmpty() || email.isEmpty() || dept.isEmpty()
                    || age <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid student details.");
                return;
            }

            Student student = new Student(
                    nextId++, name, email, dept, age);

            students.add(student);

            refreshTable();

            clearFields();

            JOptionPane.showMessageDialog(this,
                    "Student added successfully!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Age must be a valid number.");
        }
    }

    void updateStudent() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student.");
            return;
        }

        try {
            int id = (int) model.getValueAt(row, 0);

            for (Student s : students) {

                if (s.id == id) {

                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String dept = deptField.getText().trim();
                    int age = Integer.parseInt(ageField.getText().trim());

                    if (name.isEmpty() || email.isEmpty()
                            || dept.isEmpty() || age <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "Enter valid details.");
                        return;
                    }

                    s.name = name;
                    s.email = email;
                    s.department = dept;
                    s.age = age;

                    refreshTable();
                    clearFields();

                    JOptionPane.showMessageDialog(this,
                            "Student updated successfully!");

                    return;
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Enter a valid age.");
        }
    }

    void deleteStudent() {

        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a student.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        students.removeIf(s -> s.id == id);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(this,
                "Student deleted successfully!");
    }

    void searchStudent() {

        String search = searchField.getText().trim().toLowerCase();

        model.setRowCount(0);

        for (Student s : students) {

            if (s.name.toLowerCase().contains(search)) {

                model.addRow(new Object[]{
                        s.id, s.name, s.email,
                        s.department, s.age
                });
            }
        }
    }

    void refreshTable() {

        model.setRowCount(0);

        for (Student s : students) {

            model.addRow(new Object[]{
                    s.id, s.name, s.email,
                    s.department, s.age
            });
        }
    }

    void showSelectedStudent() {

        int row = table.getSelectedRow();

        if (row != -1) {

            nameField.setText(model.getValueAt(row, 1).toString());
            emailField.setText(model.getValueAt(row, 2).toString());
            deptField.setText(model.getValueAt(row, 3).toString());
            ageField.setText(model.getValueAt(row, 4).toString());
        }
    }

    void clearFields() {

        nameField.setText("");
        emailField.setText("");
        deptField.setText("");
        ageField.setText("");
        table.clearSelection();
    }

    static class Student {

        int id;
        String name;
        String email;
        String department;
        int age;

        Student(int id, String name, String email,
                String department, int age) {

            this.id = id;
            this.name = name;
            this.email = email;
            this.department = department;
            this.age = age;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new StudentManagementSystem().setVisible(true);
        });
    }
}
