package src;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/employee_inventory";
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = "115320"; // Change to your MySQL password

    private static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database.", e);
        }
    }

    // Method to get all employees from the database
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees"; // Adjust the table name as needed

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("department"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getString("hire_date")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Method to add an employee to the database
    public static void addEmployee(Employee newEmployee) {
        String query = "INSERT INTO employees (id, first_name, last_name, department, position, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, newEmployee.getId());
            pstmt.setString(2, newEmployee.getFirstName());
            pstmt.setString(3, newEmployee.getLastName());
            pstmt.setString(4, newEmployee.getDepartment());
            pstmt.setString(5, newEmployee.getPosition());
            pstmt.setDouble(6, newEmployee.getSalary());
            pstmt.setString(7, newEmployee.getHireDate());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an employee in the database
    public static void updateEmployee(Employee updatedEmployee) {
        String query = "UPDATE employees SET first_name = ?, last_name = ?, department = ?, position = ?, salary = ?, hire_date = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, updatedEmployee.getFirstName());
            pstmt.setString(2, updatedEmployee.getLastName());
            pstmt.setString(3, updatedEmployee.getDepartment());
            pstmt.setString(4, updatedEmployee.getPosition());
            pstmt.setDouble(5, updatedEmployee.getSalary());
            pstmt.setString(6, updatedEmployee.getHireDate());
            pstmt.setInt(7, updatedEmployee.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an employee from the database
    public static void deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to authenticate a user
    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to create a new user
    public static boolean createUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}