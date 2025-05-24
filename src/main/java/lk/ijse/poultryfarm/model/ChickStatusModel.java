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

    public int selectedBatchChickDeaths(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(chicks_dead) from chick_status WHERE batch_id = ? GROUP BY batch_id", batchId);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public ArrayList<ChickStatusDto> searchChickStatus(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM chick_status WHERE batch_id = ?", batchId);
        ArrayList<ChickStatusDto> chickStatusDtos = new ArrayList<>();

        while (resultSet.next()) {
            ChickStatusDto chickStatusDto = new ChickStatusDto(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
            chickStatusDtos.add(chickStatusDto);
        }
        return chickStatusDtos;
    }

    public ArrayList<ChickStatusDto> getAllChickStatus() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM chick_status ORDER BY chick_status_id DESC");

        ArrayList<ChickStatusDto> chickStatusDtos = new ArrayList<>();

        while (resultSet.next()) {
            ChickStatusDto chickStatusDto = new ChickStatusDto(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getInt(4));
            chickStatusDtos.add(chickStatusDto);
        }
        return chickStatusDtos;
    }

    public String getNextChickStatusId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT chick_status_id FROM chick_status ORDER BY chick_status_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("M%03d", nextIdNumber);
            return nextIdString;
        }

        return "M001";
    }

    public int checkStatus(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(chick_status_id) FROM chick_status WHERE date = ? GROUP BY date", date);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
