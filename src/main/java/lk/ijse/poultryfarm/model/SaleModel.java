package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.SaleDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleModel {

    public boolean saveSale(SaleDto saleDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO sale VALUES (?,?,?,?,?)", saleDto.getBatchId(),saleDto.getSaleId(),saleDto.getTotalSale(),saleDto.getDate(),saleDto.getChicksSold());
    }

    public boolean updateSale(SaleDto saleDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE sale SET batch_id = ?, total_sale = ?, date = ?, chicks_sold = ? WHERE sale_id = ?", saleDto.getBatchId(),saleDto.getTotalSale(),saleDto.getDate(),saleDto.getChicksSold(),saleDto.getSaleId());
    }

    public boolean deleteSale(String saleId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM sale WHERE sale_id = ?", saleId);
    }

    public ArrayList<SaleDto> searchSale(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM sale WHERE batch_id = ?", batchId);
        ArrayList<SaleDto> saleDtos = new ArrayList<>();

        while (resultSet.next()) {
            SaleDto saleDto = new SaleDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
            );
            saleDtos.add(saleDto);
        }
        return saleDtos;
    }

    public ArrayList<SaleDto> getAllSale() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM sale ORDER BY sale_id DESC");

        ArrayList<SaleDto> saleDtos = new ArrayList<>();

        while (resultSet.next()) {
            SaleDto saleDto = new SaleDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
            );
            saleDtos.add(saleDto);
        }
        return saleDtos;
    }

    public String getNextSaleId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT sale_id FROM sale ORDER BY sale_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("R%03d", nextIdNumber);
            return nextIdString;
        }

        return "R001";
    }

    public int selectedBatchTotalSold(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(chicks_sold) FROM sale WHERE batch_id = ? GROUP BY batch_id", batchId);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
