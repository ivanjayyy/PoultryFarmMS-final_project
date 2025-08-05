package lk.ijse.poultryfarm.dao.custom.impl;

import lk.ijse.poultryfarm.dao.custom.DailyAttendanceDAO;
import lk.ijse.poultryfarm.dto.DailyAttendanceDto;
import lk.ijse.poultryfarm.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DailyAttendanceDAOImpl implements DailyAttendanceDAO {

    public boolean saveDailyAttendance(DailyAttendanceDto dailyAttendanceDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO daily_attendance VALUES (?,?,?,?,?)", dailyAttendanceDto.getBatchId(),dailyAttendanceDto.getAttendanceId(),dailyAttendanceDto.getDate(),dailyAttendanceDto.getEmployeeId(),dailyAttendanceDto.isAttendance());
    }

    public int countAttendance(String employeeId, String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(date) from daily_attendance WHERE batch_id = ? AND employee_id = ? AND attendance = true GROUP BY attendance", batchId,employeeId);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public ArrayList<DailyAttendanceDto> searchDailyAttendance(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT da.batch_id, da.attendance_id, da.date, e.name, da.attendance FROM daily_attendance da JOIN employee e ON da.employee_id = e.employee_id WHERE da.date = ? ORDER BY da.attendance_id DESC", date);
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
        ResultSet resultSet = SQLUtil.execute("SELECT da.batch_id, da.attendance_id, da.date, e.name, da.attendance FROM daily_attendance da JOIN employee e ON da.employee_id = e.employee_id ORDER BY da.attendance_id DESC");

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
        ResultSet resultSet = SQLUtil.execute("SELECT attendance_id FROM daily_attendance ORDER BY attendance_id DESC LIMIT 1");

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

    public int checkAttendance(String date, String employeeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT COUNT(attendance_id) FROM daily_attendance WHERE date = ? AND employee_id = ? GROUP BY employee_id", date,employeeId);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public boolean deleteAttendance(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM daily_attendance WHERE attendance_id = ?", id);
    }
}
