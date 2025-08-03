package util;

import java.sql.*;

public class DBUtil {

    
    private static final String URL = "jdbc:mysql://localhost:3306/student";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = getConnection(); 
            		Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS students ("
                           + "id INT AUTO_INCREMENT PRIMARY KEY, " 
                           + "name VARCHAR(255) NOT NULL, "      
                           + "mobile VARCHAR(20), "
                           + "course VARCHAR(255)"
                           + ")";
                stmt.execute(sql);
                System.out.println("Table 'students' checked/created successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Database connection or table creation error: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure it's in your classpath.");
            e.printStackTrace();
        } finally {
            System.out.println("DBUtil static block finished.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
}