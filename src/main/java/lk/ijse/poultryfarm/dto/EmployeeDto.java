package lk.ijse.poultryfarm.dto;

public class EmployeeDto {

    private int employeeId;
    private String name;
    private String role;
    private double dailyWage;
    private String contact;
    private int ownerId;

    public EmployeeDto(int employeeId, String name, String role, double dailyWage, String contact, int ownerId) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.dailyWage = dailyWage;
        this.contact = contact;
        this.ownerId = ownerId;
    }

    public EmployeeDto() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getDailyWage() {
        return dailyWage;
    }

    public void setDailyWage(double dailyWage) {
        this.dailyWage = dailyWage;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

}
