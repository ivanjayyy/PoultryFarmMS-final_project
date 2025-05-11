package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.DailyAttendanceDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DailyAttendanceModel {

    public boolean saveDailyAttendance(DailyAttendanceDto dailyAttendanceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO daily_attendance VALUES (?,?,?,?,?)", dailyAttendanceDto.getBatchId(),dailyAttendanceDto.getAttendanceId(),dailyAttendanceDto.getDate(),dailyAttendanceDto.getEmployeeId(),dailyAttendanceDto.isAttendance());
    }

    public boolean updateDailyAttendance(DailyAttendanceDto dailyAttendanceDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE daily_attendance SET batch_id = ?, date = ?, employee_id = ?, attendance = ? WHERE attendance_id = ?", dailyAttendanceDto.getBatchId(),dailyAttendanceDto.getDate(),dailyAttendanceDto.getEmployeeId(),dailyAttendanceDto.isAttendance(),dailyAttendanceDto.getAttendanceId());
    }

    public boolean deleteDailyAttendance(String attendanceId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM daily_attendance WHERE attendance_id = ?", attendanceId);
    }

    public int countAttendance(String employeeId, String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(date) from daily_attendance WHERE batch_id = ? AND employee_id = ? AND attendance = true GROUP BY attendance", batchId,employeeId);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public ArrayList<DailyAttendanceDto> searchDailyAttendance(String employeeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM daily_attendance WHERE employee_id = ?", employeeId);
        ArrayList<DailyAttendanceDto> dailyAttendanceDtos = new ArrayList<>();

        while (resultSet.next()) {
            DailyAttendanceDto dailyAttendanceDto = new DailyAttendanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getBoolean(5)
            );
            dailyAttendanceDtos.add(dailyAttendanceDto);
        }
        return dailyAttendanceDtos;
    }

    public ArrayList<DailyAttendanceDto> getAllDailyAttendance() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM daily_attendance");

        ArrayList<DailyAttendanceDto> dailyAttendanceDtos = new ArrayList<>();

        while (resultSet.next()) {
            DailyAttendanceDto dailyAttendanceDto = new DailyAttendanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getBoolean(5)
            );
            dailyAttendanceDtos.add(dailyAttendanceDto);
        }
        return dailyAttendanceDtos;
    }

    public String getNextAttendanceId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT attendance_id FROM daily_attendance ORDER BY attendance_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("A%03d", nextIdNumber);
            return nextIdString;
        }

        return "A001";
    }
}
