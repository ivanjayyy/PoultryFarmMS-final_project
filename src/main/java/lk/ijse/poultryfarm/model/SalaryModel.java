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

    public boolean deleteSalary(String salaryId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM salary WHERE salary_id = ?", salaryId);
    }

    public ArrayList<SalaryDto> searchSalary(String employeeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT s.salary_id,e.name,s.amount,s.date from salary s join employee e on s.employee_id = e.employee_id WHERE s.employee_id = ? order by s.salary_id desc", employeeId);
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

    public ArrayList<SalaryDto> getAllSalary() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT s.salary_id,e.name,s.amount,s.date from salary s join employee e on s.employee_id = e.employee_id order by s.salary_id desc");

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

    public String getNextSalaryId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT salary_id FROM salary ORDER BY salary_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("S%03d", nextIdNumber);
            return nextIdString;
        }

        return "S001";
    }
}
