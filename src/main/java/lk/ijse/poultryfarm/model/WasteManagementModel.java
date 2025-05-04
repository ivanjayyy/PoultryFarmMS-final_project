package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.SaleDto;
import lk.ijse.poultryfarm.dto.WasteManagementDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WasteManagementModel {

    public boolean saveWasteManagement(WasteManagementDto wasteManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO waste_management VALUES (?,?,?,?)", wasteManagementDto.getBatchId(),wasteManagementDto.getWasteId(),wasteManagementDto.getTotalSale(),wasteManagementDto.getDate());
    }

    public boolean updateWasteManagement(WasteManagementDto wasteManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE waste_management SET batch_id = ?, total_sale = ?, date = ? WHERE waste_id = ?", wasteManagementDto.getBatchId(),wasteManagementDto.getTotalSale(),wasteManagementDto.getDate(),wasteManagementDto.getWasteId());
    }

    public boolean deleteWasteManagement(String wasteId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM waste_management WHERE waste_id = ?", wasteId);
    }

    public ArrayList<WasteManagementDto> searchWasteManagement(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM waste_management WHERE batch_id = ?", batchId);
        ArrayList<WasteManagementDto> wasteManagementDtos = new ArrayList<>();

        while (resultSet.next()) {
            WasteManagementDto wasteManagementDto = new WasteManagementDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            wasteManagementDtos.add(wasteManagementDto);
        }
        return wasteManagementDtos;
    }

    public ArrayList<WasteManagementDto> getAllWasteManagement() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM waste_management");

        ArrayList<WasteManagementDto> wasteManagementDtos = new ArrayList<>();

        while (resultSet.next()) {
            WasteManagementDto wasteManagementDto = new WasteManagementDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)
            );
            wasteManagementDtos.add(wasteManagementDto);
        }
        return wasteManagementDtos;
    }

    public String getNextWasteId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT waste_id FROM waste_management ORDER BY waste_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("W%03d", nextIdNumber);
            return nextIdString;
        }

        return "W001";
    }
}
