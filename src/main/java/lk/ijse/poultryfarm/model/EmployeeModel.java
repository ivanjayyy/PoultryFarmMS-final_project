package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.EmployeeDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO employee VALUES (?,?,?,?,?)", employeeDto.getEmployeeId(),employeeDto.getName(),employeeDto.isFullTime(),employeeDto.getContact(),employeeDto.getDailyWage());
    }

    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE employee SET name = ?, full_time = ?, contact = ?, daily_wage = ? WHERE employee_id = ?", employeeDto.getName(),employeeDto.isFullTime(),employeeDto.getContact(),employeeDto.getDailyWage(),employeeDto.getEmployeeId());
    }

    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM employee WHERE employee_id = ?", employeeId);
    }

    public EmployeeDto searchEmployee(String name) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee WHERE name = ?", name);
        if (resultSet.next()) {
            EmployeeDto employeeDto = new EmployeeDto(resultSet.getString(1),resultSet.getString(2),resultSet.getBoolean(3),resultSet.getString(4),resultSet.getDouble(5));
            return employeeDto;
        }
        return null;
    }

    public ArrayList<EmployeeDto> getAllEmployee() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee");

        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();

        while (resultSet.next()) {
            EmployeeDto employeeDto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBoolean(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            employeeDtos.add(employeeDto);
        }
        return employeeDtos;
    }

    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("E%03d", nextIdNumber);
            return nextIdString;
        }

        return "E001";
    }
}
