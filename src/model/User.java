package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private boolean firstLogin;

    public User(String username, String password, String role, boolean firstLogin) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstLogin = firstLogin;
    }

    public User(int id, String username, String password, String role, boolean firstLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstLogin = firstLogin;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public boolean isFirstLogin() { return firstLogin; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setFirstLogin(boolean firstLogin) { this.firstLogin = firstLogin; }
}