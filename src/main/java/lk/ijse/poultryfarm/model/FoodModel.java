package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.FoodDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodModel {

    public boolean saveFood(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO food VALUES (?,?,?)", foodDto.getFoodId(),foodDto.getFoodName(),foodDto.getQuantityRemain());
    }

    public boolean updateFood(FoodDto foodDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE food SET food_name = ?, quantity_remain = ? WHERE food_id = ?", foodDto.getFoodName(),foodDto.getQuantityRemain(),foodDto.getFoodId());
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
}
