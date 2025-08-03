package ui;

import model.Student;
import service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentForm extends JFrame {

    private JTextField tfName, tfMobile, tfCourse;
    private JTable table;
    private DefaultTableModel tableModel;

    private StudentService studentService = new StudentService();
    private int selectedId = -1;

    public StudentForm() {
        setTitle("Student Registration");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Info"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // ==== Button Panel on the left ====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnReset = new JButton("Reset");

        btnAdd.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnUpdate.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnDelete.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnReset.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonPanel.add(btnAdd);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnUpdate);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnDelete);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnReset);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        formPanel.add(buttonPanel, gbc);

        // ==== Name ====
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 2;
        tfName = new JTextField(20);
        formPanel.add(tfName, gbc);

        // ==== Mobile ====
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Mobile:"), gbc);

        gbc.gridx = 2;
        tfMobile = new JTextField(20);
        formPanel.add(tfMobile, gbc);

        // ==== Course ====
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Course:"), gbc);

        gbc.gridx = 2;
        tfCourse = new JTextField(20);
        formPanel.add(tfCourse, gbc);

        // ==== Table ====
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Mobile", "Course"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ==== Add to Frame ====
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ==== Table Click ====
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                selectedId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                tfName.setText(tableModel.getValueAt(row, 1).toString());
                tfMobile.setText(tableModel.getValueAt(row, 2).toString());
                tfCourse.setText(tableModel.getValueAt(row, 3).toString());
            }
        });

        // ==== Button Actions ====
        btnAdd.addActionListener(e -> {
            studentService.addStudent(new Student(tfName.getText(), tfMobile.getText(), tfCourse.getText()));
            loadStudentTable();
            clearForm();
        });

        btnUpdate.addActionListener(e -> {
            if (selectedId != -1) {
                studentService.updateStudent(new Student(selectedId, tfName.getText(), tfMobile.getText(), tfCourse.getText()));
                loadStudentTable();
                clearForm();
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedId != -1) {
                studentService.deleteStudent(selectedId);
                loadStudentTable();
                clearForm();
            }
        });

        btnReset.addActionListener(e -> clearForm());

        // Load initial data
        loadStudentTable();
    }

    private void loadStudentTable() {
        List<Student> students = studentService.getAllStudents();
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getMobile(), s.getCourse()});
        }
    }

    private void clearForm() {
        tfName.setText("");
        tfMobile.setText("");
        tfCourse.setText("");
        selectedId = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentForm().setVisible(true));
    }
}
