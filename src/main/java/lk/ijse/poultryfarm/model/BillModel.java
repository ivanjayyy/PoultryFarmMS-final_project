package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.BillDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillModel {

    public boolean saveBill(BillDto billDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO bill VALUES (?,?,?,?,?)", billDto.getBatchId(),billDto.getBillId(),billDto.getBillVariant(),billDto.getAmount(),billDto.getDate());
    }

    public boolean updateBill(BillDto billDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE bill SET batch_id = ?, bill_variant = ?, amount = ?, date = ? WHERE bill_id = ?", billDto.getBatchId(),billDto.getBillVariant(),billDto.getAmount(),billDto.getDate(),billDto.getBillId());
    }

    public boolean deleteBill(String billId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM bill WHERE bill_id = ?", billId);
    }

    public ArrayList<BillDto> searchBill(String billVariant) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM bill WHERE bill_variant = ? ORDER BY bill_id DESC", billVariant);

        ArrayList<BillDto> billDtos = new ArrayList<>();

        while (resultSet.next()) {
            BillDto billDto = new BillDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
            billDtos.add(billDto);
        }
        return billDtos;
    }

    public ArrayList<BillDto> getAllBill() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM bill ORDER BY bill_id DESC");

        ArrayList<BillDto> billDtos = new ArrayList<>();

        while (resultSet.next()) {
            BillDto billDto = new BillDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
            billDtos.add(billDto);
        }
        return billDtos;
    }

    public String getNextBillId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT bill_id FROM bill ORDER BY bill_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("X%03d", nextIdNumber);
            return nextIdString;
        }

        return "X001";
    }

    public int billPaidStatus(String batchId, String billType) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(bill_id) FROM bill WHERE batch_id = ? AND bill_variant = ?", batchId,billType);

        if(resultSet.next()){
            return resultSet.getInt(1);
        }

        return 0;
    }
}
