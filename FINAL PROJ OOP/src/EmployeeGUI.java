import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

// Employee class
class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private double salary;
    private String hireDate;

    public Employee(int id, String firstName, String lastName, String department, String position, double salary, String hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public String getHireDate() { return hireDate; }

    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDepartment(String department) { this.department = department; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }
}

// Custom TableModel for displaying employee data in JTable
class EmployeeTableModel extends AbstractTableModel {
    private List<Employee> employees = new ArrayList<>();
    private String[] columnNames = {"ID", "First Name", "Last Name", "Department", "Position", "Salary", "Hire Date"};

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return (employees == null) ? 0 : employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (employees == null || rowIndex < 0 || rowIndex >= employees.size()) {
            return null;
        }

        Employee employee = employees.get(rowIndex);
        switch (columnIndex) {
            case 0: return employee.getId();
            case 1: return employee.getFirstName();
            case 2: return employee.getLastName();
            case 3: return employee.getDepartment();
            case 4: return employee.getPosition();
            case 5: return employee.getSalary();
            case 6: return employee.getHireDate();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Employee getEmployeeAt(int rowIndex) {
        if (employees == null || rowIndex < 0 || rowIndex >= employees.size()) {
            return null;
        }
        return employees.get(rowIndex);
    }
}

// DatabaseUtil class for interacting with database (simplified for demonstration)
class DatabaseUtil {

    private static List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1, "John", "Doe", "IT", "Developer", 60000, "2022-01-15"));
        employees.add(new Employee(2, "Jane", "Smith", "HR", "Manager", 75000, "2021-05-23"));
        employees.add(new Employee(3, "Alice", "Johnson", "Finance", "Accountant", 50000, "2023-03-01"));
    }

    public static List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public static void addEmployee(Employee newEmployee) {
        employees.add(newEmployee);
    }

    public static void updateEmployee(Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == updatedEmployee.getId()) {
                employees.set(i, updatedEmployee);
                return;
            }
        }
    }

    public static void deleteEmployee(int id) {
        employees.removeIf(employee -> employee.getId() == id);
    }
}

// Main GUI class for Employee Management System
public class EmployeeGUI extends JFrame {

    private JTable employeeTable;
    private EmployeeTableModel tableModel;
    private JTextField idField, firstNameField, lastNameField, departmentField, positionField, salaryField, hireDateField;
    private JTextField searchField, salaryFilterField;

    public EmployeeGUI() {
        setTitle("Employee Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize table model and pass it to the JTable
        tableModel = new EmployeeTableModel();
        employeeTable = new JTable(tableModel);

        // Customize table header
        JTableHeader header = employeeTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);

        // Customize table rows
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        employeeTable.setDefaultRenderer(String.class, centerRenderer);

        // Add the table to a scroll pane for better display
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for form inputs and buttons using GroupLayout
        JPanel formPanel = new JPanel();
        GroupLayout layout = new GroupLayout(formPanel);
        formPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Form fields for Employee details
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        JLabel departmentLabel = new JLabel("Department:");
        departmentField = new JTextField();
        JLabel positionLabel = new JLabel("Position:");
        positionField = new JTextField();
        JLabel salaryLabel = new JLabel("Salary:");
        salaryField = new JTextField();
        JLabel hireDateLabel = new JLabel("Hire Date (YYYY-MM-DD):");
        hireDateField = new JTextField();

        // Search field and button
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });

        // Salary filter field and button
        JLabel salaryFilterLabel = new JLabel("Filter Salary (Above):");
        salaryFilterField = new JTextField(10);
        JButton salaryFilterButton = new JButton("Filter");
        salaryFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterBySalary();
            }
        });

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployeeTable();
            }
        });

        // Add, Update, Delete buttons
        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        JButton updateButton = new JButton("Update Employee");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        // Layout code for formPanel
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(idLabel)
                .addComponent(firstNameLabel)
                .addComponent(lastNameLabel)
                .addComponent(departmentLabel)
                .addComponent(positionLabel)
                .addComponent(salaryLabel)
                .addComponent(hireDateLabel)
                .addComponent(searchLabel)
                .addComponent(salaryFilterLabel))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(idField)
                .addComponent(firstNameField)
                .addComponent(lastNameField)
                .addComponent(departmentField)
                .addComponent(positionField)
                .addComponent(salaryField)
                .addComponent(hireDateField)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(searchField)
                    .addComponent(searchButton))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(salaryFilterField)
                    .addComponent(salaryFilterButton))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(addButton)
                    .addComponent(updateButton)
                    .addComponent(deleteButton)
                    .addComponent(refreshButton)))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(idLabel)
                .addComponent(idField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(firstNameLabel)
                .addComponent(firstNameField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lastNameLabel)
                .addComponent(lastNameField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(departmentLabel)
                .addComponent(departmentField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(positionLabel)
                .addComponent(positionField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(salaryLabel)
                .addComponent(salaryField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(hireDateLabel)
                .addComponent(hireDateField))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(searchLabel)
                .addComponent(searchField)
                .addComponent(searchButton))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(salaryFilterLabel)
                .addComponent(salaryFilterField)
                .addComponent(salaryFilterButton))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(addButton)
                .addComponent(updateButton)
                .addComponent(deleteButton)
                .addComponent(refreshButton))
        );

        add(formPanel, BorderLayout.SOUTH);

        // Initial data load
        updateEmployeeTable();

        // Add mouse listener to the table to reflect selected employee's information in the text fields
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Employee selectedEmployee = tableModel.getEmployeeAt(selectedRow);
                    idField.setText(String.valueOf(selectedEmployee.getId()));
                    firstNameField.setText(selectedEmployee.getFirstName());
                    lastNameField.setText(selectedEmployee.getLastName());
                    departmentField.setText(selectedEmployee.getDepartment());
                    positionField.setText(selectedEmployee.getPosition());
                    salaryField.setText(String.valueOf(selectedEmployee.getSalary()));
                    hireDateField.setText(selectedEmployee.getHireDate());
                }
            }
        });
    }

    // Method to search employee by various fields
    private void searchEmployee() {
        String searchText = searchField.getText().toLowerCase();
        List<Employee> employees = DatabaseUtil.getAllEmployees().stream()
            .filter(e -> String.valueOf(e.getId()).contains(searchText) ||
                         e.getFirstName().toLowerCase().contains(searchText) ||
                         e.getLastName().toLowerCase().contains(searchText) ||
                         e.getDepartment().toLowerCase().contains(searchText) ||
                         e.getPosition().toLowerCase().contains(searchText) ||
                         e.getHireDate().toLowerCase().contains(searchText))
            .collect(Collectors.toList());
        tableModel.setEmployees(employees);
    }

    // Method to filter employees by salary
    private void filterBySalary() {
        try {
            double salaryThreshold = Double.parseDouble(salaryFilterField.getText());
            List<Employee> employees = DatabaseUtil.getAllEmployees().stream()
                .filter(e -> e.getSalary() > salaryThreshold)
                .collect(Collectors.toList());
            tableModel.setEmployees(employees);
            JOptionPane.showMessageDialog(this, "Filtered employees with salary above " + salaryThreshold);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid salary amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to add an employee
    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String department = departmentField.getText();
            String position = positionField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            String hireDate = hireDateField.getText();

            Employee newEmployee = new Employee(id, firstName, lastName, department, position, salary, hireDate);
            DatabaseUtil.addEmployee(newEmployee);
            updateEmployeeTable();
            JOptionPane.showMessageDialog(this, "Employee added successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid data.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while adding the employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update an employee
    private void updateEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String department = departmentField.getText();
            String position = positionField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            String hireDate = hireDateField.getText();

            Employee updatedEmployee = new Employee(id, firstName, lastName, department, position, salary, hireDate);
            DatabaseUtil.updateEmployee(updatedEmployee);
            updateEmployeeTable();
            JOptionPane.showMessageDialog(this, "Employee updated successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid data.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while updating the employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete an employee
    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            DatabaseUtil.deleteEmployee(id);
            updateEmployeeTable();
            JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID. Please enter a valid ID.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while deleting the employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployeeTable() {
        List<Employee> employees = DatabaseUtil.getAllEmployees();
        tableModel.setEmployees(employees);
    }

    private void clearFields() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        departmentField.setText("");
        positionField.setText("");
        salaryField.setText("");
        hireDateField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeeGUI().setVisible(true);
        });
    }
}