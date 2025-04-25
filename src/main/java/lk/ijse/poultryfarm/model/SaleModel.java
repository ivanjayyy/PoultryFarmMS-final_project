package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.SalaryDto;
import lk.ijse.poultryfarm.dto.SaleDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleModel {

    public boolean saveSale(SaleDto saleDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO sale VALUES (?,?,?,?)", saleDto.getBatchId(),saleDto.getSaleId(),saleDto.getTotalSale(),saleDto.getDate());
    }

    public boolean updateSale(SaleDto saleDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE sale SET batch_id = ?, total_sale = ?, date = ? WHERE sale_id = ?", saleDto.getBatchId(),saleDto.getTotalSale(),saleDto.getDate(),saleDto.getSaleId());
    }

    public boolean deleteSale(String saleId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM sale WHERE sale_id = ?", saleId);
    }

    public SaleDto searchSale(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM sale WHERE batch_id = ?", batchId);
        if (resultSet.next()) {
            SaleDto saleDto = new SaleDto(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4));
            return saleDto;
        }
        return null;
    }

    public ArrayList<SaleDto> getAllSale() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM sale");

        ArrayList<SaleDto> saleDtos = new ArrayList<>();

        while (resultSet.next()) {
            SaleDto saleDto = new SaleDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            saleDtos.add(saleDto);
        }
        return saleDtos;
    }
}
