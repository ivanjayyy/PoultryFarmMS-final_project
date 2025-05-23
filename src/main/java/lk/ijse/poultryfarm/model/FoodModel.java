package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.FoodConsumptionDto;
import lk.ijse.poultryfarm.dto.FoodDto;
import lk.ijse.poultryfarm.dto.FoodPaymentDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodModel {

    public boolean saveFood(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO food VALUES (?,?,?)", foodDto.getFoodId(),foodDto.getFoodName(),foodDto.getQuantityRemain());
    }

    public boolean updateAfterFoodConsumption(FoodConsumptionDto foodConsumptionDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE food SET quantity_remain = (quantity_remain - ?) WHERE food_id = ?", foodConsumptionDto.getConsumption(),foodConsumptionDto.getFoodId());
    }

    public boolean updateAfterFoodOrder(FoodPaymentDto foodPaymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE food SET quantity_remain = (quantity_remain + ?) WHERE food_id = ?", foodPaymentDto.getQuantity(),foodPaymentDto.getFoodId());
    }

    public boolean deleteFood(String foodId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM food WHERE food_id = ?", foodId);
    }

    public String foodInventory(String foodId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT quantity_remain FROM food WHERE food_id = ?", foodId);

        if (resultSet.next()) {
            String foodRemain = resultSet.getString(1);
            return foodRemain;
        }

        return null;
    }

    public ArrayList<FoodDto> getAllFood() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM food");

        ArrayList<FoodDto> foodDtos = new ArrayList<>();

        while (resultSet.next()) {
            FoodDto foodDto = new FoodDto(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3));
            foodDtos.add(foodDto);
        }
        return foodDtos;
    }

    public String getNextFoodId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT food_id FROM food ORDER BY food_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("F%03d", nextIdNumber);
            return nextIdString;
        }

        return "F001";
    }

    public String getFoodId(String foodName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT food_id FROM food WHERE food_name = ?", foodName);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
