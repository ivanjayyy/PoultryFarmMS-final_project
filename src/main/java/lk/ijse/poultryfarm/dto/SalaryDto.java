package lk.ijse.poultryfarm.dto;

public class SalaryDto {

    private int salaryId;
    private String date;
    private int employeeId;

    public SalaryDto(int salaryId, String date, int employeeId) {
        this.salaryId = salaryId;
        this.date = date;
        this.employeeId = employeeId;
    }

    public SalaryDto() {
    }

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}
