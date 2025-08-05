package lk.ijse.poultryfarm.dao.custom.impl;

import lk.ijse.poultryfarm.dao.custom.FoodDAO;
import lk.ijse.poultryfarm.dto.FoodConsumptionDto;
import lk.ijse.poultryfarm.dto.FoodPaymentDto;
import lk.ijse.poultryfarm.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodDAOImpl implements FoodDAO {

    public boolean updateAfterFoodConsumption(FoodConsumptionDto foodConsumptionDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE food SET quantity_remain = (quantity_remain - ?) WHERE food_id = ?", foodConsumptionDto.getConsumption(),foodConsumptionDto.getFoodId());
    }

    public boolean updateAfterFoodOrder(FoodPaymentDto foodPaymentDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE food SET quantity_remain = (quantity_remain + ?) WHERE food_id = ?", foodPaymentDto.getQuantity(),foodPaymentDto.getFoodId());
    }

    public String foodInventory(String foodId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT quantity_remain FROM food WHERE food_id = ?", foodId);

        if (resultSet.next()) {
            String foodRemain = resultSet.getString(1);
            return foodRemain;
        }

        return null;
    }

    public String getFoodId(String foodName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT food_id FROM food WHERE food_name = ?", foodName);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public String getFoodName(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT food_name FROM food WHERE food_id = ?", id);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
