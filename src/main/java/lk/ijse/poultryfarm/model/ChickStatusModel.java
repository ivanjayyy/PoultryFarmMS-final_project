package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.ChickStatusDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChickStatusModel {

    public boolean saveChickStatus(ChickStatusDto chickStatusDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO chick_status VALUES (?,?,?,?)", chickStatusDto.getBatchId(),chickStatusDto.getChickStatusId(),chickStatusDto.getDate(),chickStatusDto.getChicksDead());
    }

    public boolean updateChickStatus(ChickStatusDto chickStatusDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE chick_status SET batch_id = ?, date = ?, chicks_dead = ? WHERE chick_status_id = ?", chickStatusDto.getBatchId(),chickStatusDto.getDate(),chickStatusDto.getChicksDead(),chickStatusDto.getChickStatusId());
    }

    public boolean deleteChickStatus(String chickStatusId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM chick_status WHERE chick_status_id = ?", chickStatusId);
    }

    public ChickStatusDto searchChickStatus(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM chick_status WHERE date = ?", date);
        if (resultSet.next()) {
            ChickStatusDto chickStatusDto = new ChickStatusDto(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
            return chickStatusDto;
        }
        return null;
    }

    public ArrayList<ChickStatusDto> getAllChickStatus() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM chick_status");

        ArrayList<ChickStatusDto> chickStatusDtos = new ArrayList<>();

        while (resultSet.next()) {
            ChickStatusDto chickStatusDto = new ChickStatusDto(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
            chickStatusDtos.add(chickStatusDto);
        }
        return chickStatusDtos;
    }
}
