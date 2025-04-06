package lk.ijse.poultryfarm.dto;

public class DailyAttendanceDto {

    private int attendanceId;
    private String date;
    private boolean attendance;
    private int employeeId;

    public DailyAttendanceDto() {
    }

    public DailyAttendanceDto(int attendanceId, String date, boolean attendance, int employeeId) {
        this.attendanceId = attendanceId;
        this.date = date;
        this.attendance = attendance;
        this.employeeId = employeeId;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAttendance() {
        return attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}
