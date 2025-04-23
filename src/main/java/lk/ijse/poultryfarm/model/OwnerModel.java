package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.database.DBConnection;
import lk.ijse.poultryfarm.dto.OwnerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OwnerModel {
    public String saveOwner(OwnerDto ownerDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        String query = "INSERT INTO owner VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ownerDto.getName());
        preparedStatement.setString(2, ownerDto.getPassword());
        preparedStatement.setString(3, ownerDto.getEmail());
        preparedStatement.setString(4, ownerDto.getUsername());

        return preparedStatement.executeUpdate() > 0 ? "Successful" : "Unsuccessful";
    }
}
