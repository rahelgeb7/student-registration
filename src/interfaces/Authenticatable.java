package interfaces;

import model.User;

public interface Authenticatable {

    boolean authenticate(String username, String password);

    void registerUser(String username, String password, String role);

    void changePassword(String username, String oldPassword, String newPassword);
    
}