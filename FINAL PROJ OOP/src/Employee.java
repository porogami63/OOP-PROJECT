public class Employee implements Comparable<Employee> {
    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private String position;
    private double salary;
    private String hireDate;

    // Constructor
    public Employee(int id, String firstName, String lastName, String department, String position, double salary, String hireDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters and Setters with validation
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public String getFirstName() { 
        return firstName; 
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
    }

    public String getLastName() { 
        return lastName; 
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.trim().isEmpty()) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
    }

    public String getDepartment() { 
        return department; 
    }

    public void setDepartment(String department) {
        if (department != null && !department.trim().isEmpty()) {
            this.department = department;
        } else {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
    }

    public String getPosition() { 
        return position; 
    }

    public void setPosition(String position) {
        if (position != null && !position.trim().isEmpty()) {
            this.position = position;
        } else {
            throw new IllegalArgumentException("Position cannot be null or empty");
        }
    }

    public double getSalary() { 
        return salary; 
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            throw new IllegalArgumentException("Salary must be non-negative");
        }
    }

    public String getHireDate() { 
        return hireDate; 
    }

    public void setHireDate(String hireDate) {
        if (hireDate != null && !hireDate.trim().isEmpty()) {
            this.hireDate = hireDate;
        } else {
            throw new IllegalArgumentException("Hire date cannot be null or empty");
        }
    }

    // Override toString method
    @Override
    public String toString() {
        return "Employee{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName + "', department='" + department + "', position='" + position + "', salary=" + salary + ", hireDate='" + hireDate + "'}";
    }

    // Implement compareTo method for sorting by ID
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id); // Sorting by employee ID
    }
}
