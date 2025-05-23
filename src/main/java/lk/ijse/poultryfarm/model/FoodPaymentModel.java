package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.FoodPaymentDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodPaymentModel {

    public boolean saveFoodPayment(FoodPaymentDto foodPaymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO food_payment VALUES (?,?,?,?,?)", foodPaymentDto.getFoodPaymentId(),foodPaymentDto.getFoodId(),foodPaymentDto.getQuantity(),foodPaymentDto.getPayAmount(),foodPaymentDto.getDate());
    }

    public boolean updateFoodPayment(FoodPaymentDto foodPaymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE food_payment SET food_id = ?, quantity = ?, pay_amount = ?, date = ? WHERE food_payment_id = ?", foodPaymentDto.getFoodId(),foodPaymentDto.getQuantity(),foodPaymentDto.getPayAmount(),foodPaymentDto.getDate(),foodPaymentDto.getFoodPaymentId());
    }

    public boolean deleteFoodPayment(String foodPaymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM food_payment WHERE food_payment_id = ?", foodPaymentId);
    }

    public ArrayList<FoodPaymentDto> searchFoodPayment(String foodId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM food_payment WHERE food_id = ?", foodId);
        ArrayList<FoodPaymentDto> foodPaymentDtos = new ArrayList<>();

        while (resultSet.next()) {
            FoodPaymentDto foodPaymentDto = new FoodPaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
            foodPaymentDtos.add(foodPaymentDto);
        }
        return foodPaymentDtos;
    }

    public ArrayList<FoodPaymentDto> getAllFoodPayment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM food_payment ORDER BY food_payment_id DESC");

        ArrayList<FoodPaymentDto> foodPaymentDtos = new ArrayList<>();

        while (resultSet.next()) {
            FoodPaymentDto foodPaymentDto = new FoodPaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
            foodPaymentDtos.add(foodPaymentDto);
        }
        return foodPaymentDtos;
    }

    public String getNextFoodPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT food_payment_id FROM food_payment ORDER BY food_payment_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format("P%03d", nextIdNumber);
            return nextIdString;
        }

        return "P001";
    }
}
