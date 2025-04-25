package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.SalaryDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryModel {

    public boolean saveSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO salary VALUES (?,?,?,?)", salaryDto.getSalaryId(),salaryDto.getEmployeeId(),salaryDto.getAmount(),salaryDto.getDate());
    }

    public boolean updateSalary(SalaryDto salaryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE salary SET employee_id = ?, amount = ?, date = ? WHERE salary_id = ?", salaryDto.getEmployeeId(),salaryDto.getAmount(),salaryDto.getDate(),salaryDto.getSalaryId());
    }

    public boolean deleteSalary(String salaryId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM salary WHERE salary_id = ?", salaryId);
    }

    public SalaryDto searchSalary(String employeeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM salary WHERE employee_id = ?", employeeId);
        if (resultSet.next()) {
            SalaryDto salaryDto = new SalaryDto(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4));
            return salaryDto;
        }
        return null;
    }

    public ArrayList<SalaryDto> getAllSalary() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM salary");

        ArrayList<SalaryDto> salaryDtos = new ArrayList<>();

        while (resultSet.next()) {
            SalaryDto salaryDto = new SalaryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            salaryDtos.add(salaryDto);
        }
        return salaryDtos;
    }
}
