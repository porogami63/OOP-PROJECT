package src;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "First Name", "Last Name", "Department", "Position", "Salary", "Hire Date"};
    private List<Employee> employees;

    public EmployeeTableModel() {
        this.employees = DatabaseUtil.getAllEmployees();
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return employee.getId();
            case 1:
                return employee.getFirstName();
            case 2:
                return employee.getLastName();
            case 3:
                return employee.getDepartment();
            case 4:
                return employee.getPosition();
            case 5:
                return employee.getSalary();
            case 6:
                return employee.getHireDate();
            default:
                return null;
        }
    }

    public Employee getEmployeeAt(int rowIndex) {
        return employees.get(rowIndex);
    }
}