package lk.ijse.poultryfarm.dao.custom.impl;

import lk.ijse.poultryfarm.dao.custom.FoodPaymentDAO;
import lk.ijse.poultryfarm.database.DBConnection;
import lk.ijse.poultryfarm.dto.FoodPaymentDto;
import lk.ijse.poultryfarm.dao.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodPaymentDAOImpl implements FoodPaymentDAO {

    public boolean saveFoodPayment(FoodPaymentDto foodPaymentDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isSaved = SQLUtil.execute("INSERT INTO food_payment VALUES (?,?,?,?,?)", foodPaymentDto.getFoodPaymentId(),foodPaymentDto.getFoodId(),foodPaymentDto.getQuantity(),foodPaymentDto.getPayAmount(),foodPaymentDto.getDate());

            if (isSaved) {
                FoodDAOImpl foodModel = new FoodDAOImpl();
                boolean isUpdated = foodModel.updateAfterFoodOrder(foodPaymentDto);

                if (isUpdated) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public ArrayList<FoodPaymentDto> searchFoodPayment(String foodId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select fp.food_payment_id,f.food_name,fp.quantity,fp.pay_amount,fp.date from food_payment fp join food f on fp.food_id = f.food_id WHERE fp.food_id = ? order by fp.food_payment_id desc", foodId);
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
        ResultSet resultSet = SQLUtil.execute("select fp.food_payment_id,f.food_name,fp.quantity,fp.pay_amount,fp.date from food_payment fp join food f on fp.food_id = f.food_id order by fp.food_payment_id desc");

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
        ResultSet resultSet = SQLUtil.execute("SELECT food_payment_id FROM food_payment ORDER BY food_payment_id DESC LIMIT 1");

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
