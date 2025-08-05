package lk.ijse.poultryfarm.dao.custom.impl;

import lk.ijse.poultryfarm.dao.custom.FoodConsumptionDAO;
import lk.ijse.poultryfarm.database.DBConnection;
import lk.ijse.poultryfarm.dto.FoodConsumptionDto;
import lk.ijse.poultryfarm.dao.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FoodConsumptionDAOImpl implements FoodConsumptionDAO {

    public boolean saveFoodConsumption(FoodConsumptionDto foodConsumptionDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            boolean isSaved = SQLUtil.execute("INSERT INTO food_consumption VALUES (?,?,?,?,?)", foodConsumptionDto.getBatchId(),foodConsumptionDto.getConsumptionId(),foodConsumptionDto.getDate(),foodConsumptionDto.getFoodId(),foodConsumptionDto.getConsumption());

            if (isSaved) {
                FoodDAOImpl foodModel = new FoodDAOImpl();
                boolean isUpdated = foodModel.updateAfterFoodConsumption(foodConsumptionDto);

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

    public ArrayList<FoodConsumptionDto> searchFoodConsumption(String batchId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("select fc.batch_id,fc.consumption_id,fc.date,f.food_name,fc.consumption from food_consumption fc join food f on fc.food_id = f.food_id WHERE fc.batch_id = ? order by fc.consumption_id desc", batchId);
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
        ResultSet resultSet = SQLUtil.execute("select fc.batch_id,fc.consumption_id,fc.date,f.food_name,fc.consumption from food_consumption fc join food f on fc.food_id = f.food_id order by fc.consumption_id desc");

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
        ResultSet resultSet = SQLUtil.execute("SELECT consumption_id FROM food_consumption ORDER BY consumption_id DESC LIMIT 1");

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
