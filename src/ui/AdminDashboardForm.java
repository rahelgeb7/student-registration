package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardForm extends JFrame {
    private JFrame parentFrame; // Added reference to the parent frame

    public AdminDashboardForm(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton createUserButton = new JButton("Create New User");
        createUserButton.setFont(new Font("Arial", Font.BOLD, 16));
        createUserButton.setPreferredSize(new Dimension(200, 50));
        panel.add(createUserButton);

        JButton manageStudentsButton = new JButton("Manage Students");
        manageStudentsButton.setFont(new Font("Arial", Font.BOLD, 16));
        manageStudentsButton.setPreferredSize(new Dimension(200, 50));
        panel.add(manageStudentsButton);

        JButton dataManagementButton = new JButton("Manage Data (CSV)");
        dataManagementButton.setFont(new Font("Arial", Font.BOLD, 16));
        dataManagementButton.setPreferredSize(new Dimension(250, 50));
        panel.add(dataManagementButton);
        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(100, 50));
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);

        createUserButton.addActionListener(e -> {
            setVisible(false);
            new CreateUserForm(this).setVisible(true);
        });

        manageStudentsButton.addActionListener(e -> {
            setVisible(false);
            new StudentForm(this).setVisible(true);
        });
        
        dataManagementButton.addActionListener(e -> {
            JDialog dialog = new JDialog(AdminDashboardForm.this, "CSV Data Management", true);
            dialog.setSize(300, 200);
            dialog.setLocationRelativeTo(AdminDashboardForm.this);
            dialog.add(new StudentDataPanel());
            dialog.setVisible(true);
        });

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
    }
}