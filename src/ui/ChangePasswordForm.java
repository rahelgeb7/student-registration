package ui;

import service.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordForm extends JDialog {
    private JFrame parentFrame;
    private String username;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changePasswordButton;
    private JButton backButton;
    private UserService userService;

    public ChangePasswordForm(JFrame parentFrame, String username) {
        super(parentFrame, "Change Your Password", true);
        this.parentFrame = parentFrame;
        this.username = username;
        userService = new UserService();

        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Welcome, " + username + "! Please change your password."));
        panel.add(new JLabel());

        panel.add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField();
        panel.add(newPasswordField);

        panel.add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        panel.add(confirmPasswordField);

        changePasswordButton = new JButton("Change Password");
        backButton = new JButton("Back to Login");

        panel.add(changePasswordButton);
        panel.add(backButton);

        add(panel);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(ChangePasswordForm.this, "Password fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(ChangePasswordForm.this, "New passwords do not match.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userService.forcePasswordChange(username, newPassword)) {
                    JOptionPane.showMessageDialog(ChangePasswordForm.this, "Password changed successfully! You can now proceed.");
                    dispose();
                    new StudentForm(parentFrame).setVisible(true); // Now passes the parent frame
                } else {
                    JOptionPane.showMessageDialog(ChangePasswordForm.this, "Failed to change password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
    }
}