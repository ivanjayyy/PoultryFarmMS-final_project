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

    public FoodConsumptionDto searchFoodConsumption(String foodId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM food_consumption WHERE food_id = ?", foodId);
        if (resultSet.next()) {
            FoodConsumptionDto foodConsumptionDto = new FoodConsumptionDto(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getDouble(5));
            return foodConsumptionDto;
        }
        return null;
    }

    public ArrayList<FoodConsumptionDto> getAllFoodConsumption() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM food_consumption");

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
}
