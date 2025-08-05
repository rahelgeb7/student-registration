package ui;

import service.UserService;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private UserService userService;

    public LoginForm() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userService = new UserService();
        userService.initializeDefaultAdmin();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (userService.authenticate(username, password)) {
                    User loggedInUser = userService.getUserByUsername(username);
                    if (loggedInUser != null) {
                        JOptionPane.showMessageDialog(LoginForm.this, "Login Successful!");
                        setVisible(false); // Hide this form

                        if (loggedInUser.isFirstLogin()) {
                            new ChangePasswordForm(LoginForm.this, loggedInUser.getUsername()).setVisible(true);
                        } else if ("admin".equalsIgnoreCase(loggedInUser.getRole())) {
                            new AdminDashboardForm(LoginForm.this).setVisible(true);
                        } else {
                            new StudentForm(LoginForm.this).setVisible(true);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Invalid Username or Password!", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}