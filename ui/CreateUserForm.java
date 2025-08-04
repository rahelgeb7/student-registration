package ui;

import service.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserForm extends JDialog {
    private JFrame parentFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private JButton createUserButton;
    private JButton backButton;
    private UserService userService;

    public CreateUserForm(JFrame parentFrame) {
        super(parentFrame, "Create New User", true);
        this.parentFrame = parentFrame;
        userService = new UserService();

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Initial Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);

        panel.add(new JLabel("Role:"));
        String[] roles = {"user"};
        roleComboBox = new JComboBox<>(roles);
        panel.add(roleComboBox);

        createUserButton = new JButton("Create User");
        backButton = new JButton("Back");
        
        panel.add(createUserButton);
        panel.add(backButton);
        
        add(panel);

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(CreateUserForm.this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(CreateUserForm.this, "Passwords do not match.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                boolean firstLogin = true;

                if (userService.registerUser(username, password, role, firstLogin)) {
                    JOptionPane.showMessageDialog(CreateUserForm.this, "User '" + username + "' created successfully!");
                    usernameField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                    roleComboBox.setSelectedItem("user");
                } else {
                    JOptionPane.showMessageDialog(CreateUserForm.this, "Failed to create user. Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        backButton.addActionListener(e -> {
            dispose();
        });
    }
}