package service;

import DAO.UserDAO;
import model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean authenticate(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        return false;
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    // Reverted method signature
    public boolean registerUser(String username, String rawPassword, String role, boolean firstLogin) {
        if (userDAO.getUserByUsername(username) != null) {
            System.out.println("Username '" + username + "' already exists.");
            return false;
        }
        User newUser = new User(username, rawPassword, role, firstLogin);
        return userDAO.createUser(newUser);
    }
    
    // Reverted method signature
    public boolean changePassword(String username, String oldRawPassword, String newRawPassword) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && oldRawPassword.equals(user.getPassword())) {
            return userDAO.updatePasswordAndFirstLogin(username, newRawPassword);
        }
        return false;
    }

    public boolean forcePasswordChange(String username, String newRawPassword) {
        return userDAO.updatePasswordAndFirstLogin(username, newRawPassword);
    }

    public boolean updateUserRole(String username, String newRole) {
        return userDAO.updateUserRole(username, newRole);
    }

    public void initializeDefaultAdmin() {
        if (userDAO.getUserByUsername("admin") == null) {
            System.out.println("Creating default admin user: admin/adminpass");
            registerUser("admin", "adminpass", "admin", false);
        } else {
            System.out.println("Default admin user 'admin' already exists.");
        }
    }
}