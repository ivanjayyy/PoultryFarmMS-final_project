package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.FoodConsumptionDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodConsumptionModel {

    public boolean saveFoodConsumption(FoodConsumptionDto foodConsumptionDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO food_consumption VALUES (?,?,?,?,?)", foodConsumptionDto.getBatchId(),foodConsumptionDto.getConsumptionId(),foodConsumptionDto.getDate(),foodConsumptionDto.getFoodId(),foodConsumptionDto.getConsumption());
    }

    public boolean updateFoodConsumption(FoodConsumptionDto foodConsumptionDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE food_consumption SET batch_id = ?, date = ?, food_id = ?, consumption = ? WHERE consumption_id = ?", foodConsumptionDto.getBatchId(),foodConsumptionDto.getDate(),foodConsumptionDto.getFoodId(),foodConsumptionDto.getConsumption(),foodConsumptionDto.getConsumptionId());
    }

    public boolean deleteFoodConsumption(String consumptionId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM food_consumption WHERE consumption_id = ?", consumptionId);
    }

    public ArrayList<FoodConsumptionDto> searchFoodConsumption(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select fc.batch_id,fc.consumption_id,fc.date,f.food_name,fc.consumption from food_consumption fc join food f on fc.food_id = f.food_id WHERE fc.batch_id = ? order by fc.consumption_id desc", batchId);
        ArrayList<FoodConsumptionDto> foodConsumptionDtos = new ArrayList<>();

        while (resultSet.next()) {
            FoodConsumptionDto foodConsumptionDto = new FoodConsumptionDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            foodConsumptionDtos.add(foodConsumptionDto);
        }
        return foodConsumptionDtos;
    }

    public ArrayList<FoodConsumptionDto> getAllFoodConsumption() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select fc.batch_id,fc.consumption_id,fc.date,f.food_name,fc.consumption from food_consumption fc join food f on fc.food_id = f.food_id order by fc.consumption_id desc");

        ArrayList<FoodConsumptionDto> foodConsumptionDtos = new ArrayList<>();

        while (resultSet.next()) {
            FoodConsumptionDto foodConsumptionDto = new FoodConsumptionDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            );
            foodConsumptionDtos.add(foodConsumptionDto);
        }
        return foodConsumptionDtos;
    }

    public String getNextConsumptionId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT consumption_id FROM food_consumption ORDER BY consumption_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("C%03d", nextIdNumber);
            return nextIdString;
        }

        return "C001";
    }
}
