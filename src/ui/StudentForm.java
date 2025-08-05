package ui;

import model.Student;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class StudentForm extends JFrame {
    private JFrame parentFrame;
    private StudentService studentService;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField nameField;
    private JTextField mobileField;
    private JTextField courseField;
    private JButton addBtn;
    private JButton updateBtn;
    private JButton deleteBtn;
    private JButton backButton;
    
    private int selectedStudentId = -1;

    public StudentForm(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        studentService = new StudentService();
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // --- Table Panel ---
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Mobile", "Course"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // --- Input Panel ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Mobile:"));
        mobileField = new JTextField();
        inputPanel.add(mobileField);
        
        inputPanel.add(new JLabel("Course:"));
        courseField = new JTextField();
        inputPanel.add(courseField);
        
        // --- Buttons Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addBtn = new JButton("Add Student");
        updateBtn = new JButton("Update Student");
        deleteBtn = new JButton("Delete Student");
        backButton = new JButton("Back");
        
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backButton);

        // --- Main Control Panel (Input and Buttons) ---
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.SOUTH);
        
        // --- Load initial data ---
        loadStudentData();
        
        // --- Action Listeners ---
        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedStudentId = (int) tableModel.getValueAt(selectedRow, 0);
                    nameField.setText((String) tableModel.getValueAt(selectedRow, 1));
                    mobileField.setText((String) tableModel.getValueAt(selectedRow, 2));
                    courseField.setText((String) tableModel.getValueAt(selectedRow, 3));
                }
            }
        });
        
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
    }
    
    private void loadStudentData() {
        tableModel.setRowCount(0);
        List<Student> students = studentService.getAllStudents();
        for (Student student : students) {
            Vector<Object> row = new Vector<>();
            row.add(student.getId());
            row.add(student.getName());
            row.add(student.getMobile());
            row.add(student.getCourse());
            tableModel.addRow(row);
        }
    }
    
    private void addStudent() {
        String name = nameField.getText().trim();
        String mobile = mobileField.getText().trim();
        String course = courseField.getText().trim();
        
        if (name.isEmpty() || mobile.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Student newStudent = new Student(name, mobile, course);
        studentService.addStudent(newStudent);
        loadStudentData();
        clearFields();
    }
    
    private void updateStudent() {
        if (selectedStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to update.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String name = nameField.getText().trim();
        String mobile = mobileField.getText().trim();
        String course = courseField.getText().trim();
        
        if (name.isEmpty() || mobile.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Student updatedStudent = new Student(selectedStudentId, name, mobile, course);
        studentService.updateStudent(updatedStudent);
        loadStudentData();
        clearFields();
    }
    
    private void deleteStudent() {
        if (selectedStudentId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            studentService.deleteStudent(selectedStudentId);
            loadStudentData();
            clearFields();
        }
    }
    
    private void clearFields() {
        nameField.setText("");
        mobileField.setText("");
        courseField.setText("");
        selectedStudentId = -1;
    }
}