package lk.ijse.poultryfarm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.poultryfarm.dto.ChickBatchDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChickBatchModel {

    public boolean saveChickBatch(ChickBatchDto chickBatchDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO chick_batch VALUES (?,?,?,?)", chickBatchDto.getBatchId(),chickBatchDto.getChickTotal(),chickBatchDto.getPayment(),chickBatchDto.getDate());
    }

    public boolean updateChickBatch(ChickBatchDto chickBatchDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE chick_batch SET chick_total = ?, payment = ?, date = ? WHERE batch_id = ?", chickBatchDto.getChickTotal(),chickBatchDto.getPayment(),chickBatchDto.getDate(),chickBatchDto.getBatchId());
    }

    public ArrayList<ChickBatchDto> searchChickBatch(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM chick_batch WHERE batch_id = ?", batchId);

        ArrayList<ChickBatchDto> chickBatchDtos = new ArrayList<>();

        while (resultSet.next()) {
            ChickBatchDto chickBatchDto = new ChickBatchDto(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            chickBatchDtos.add(chickBatchDto);
        }
        return chickBatchDtos;
    }

    public ArrayList<ChickBatchDto> getAllChickBatch() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM chick_batch ORDER BY batch_id DESC");

        ArrayList<ChickBatchDto> chickBatchDtos = new ArrayList<>();

        while (resultSet.next()) {
            ChickBatchDto chickBatchDto = new ChickBatchDto(
                    resultSet.getString(1),
                    resultSet.getInt(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            chickBatchDtos.add(chickBatchDto);
        }
        return chickBatchDtos;
    }

    public String getNextBatchId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT batch_id FROM chick_batch ORDER BY batch_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("B%03d", nextIdNumber);
            return nextIdString;
        }

        return "B001";
    }

    public String getCurrentBatchId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT batch_id FROM chick_batch ORDER BY batch_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            return lastId;
        }

        return null;
    }

    public int getChickTotal(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT chick_total FROM chick_batch WHERE batch_id = ?", batchId);

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return -1;
    }

    public ObservableList<String> getAllBatchIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT batch_id FROM chick_batch ORDER BY batch_id DESC");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }

        ObservableList<String> batchIds = FXCollections.observableArrayList();
        batchIds.addAll(list);
        return batchIds;
    }
}
