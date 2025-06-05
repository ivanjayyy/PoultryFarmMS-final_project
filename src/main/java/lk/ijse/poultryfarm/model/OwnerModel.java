package lk.ijse.poultryfarm.model;

import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerModel {

    public boolean saveOwner(OwnerDto ownerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO owner VALUES (?,?,?,?,?)", ownerDto.getOwnerId(),ownerDto.getName(),ownerDto.getUsername(),ownerDto.getPassword(),ownerDto.getEmail());
    }

    public boolean updateOwner(OwnerDto ownerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE owner SET name = ?, username = ?, password = ?, email = ? WHERE owner_id = ?", ownerDto.getName(),ownerDto.getUsername(),ownerDto.getPassword(),ownerDto.getEmail(),ownerDto.getOwnerId());
    }

    public String ownerUsername() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT username FROM owner WHERE owner_id = 'O001'");
        if (resultSet.next()) {
            return resultSet.getString("username");
        }
        return null;
    }

    public String ownerPassword() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT password FROM owner WHERE owner_id = 'O001'");
        if (resultSet.next()) {
            return resultSet.getString("password");
        }
        return null;
    }

    public OwnerDto getOwner() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM owner WHERE owner_id = 'O001'");

        if (resultSet.next()) {
            return new OwnerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public boolean hasOwner() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(owner_id) FROM owner");
        if (resultSet.next()) {
            if(resultSet.getInt(1)>0){
                return true;
            }
        }
        return false;
    }

    public String getEmail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT email FROM owner WHERE owner_id = 'O001'");
        if (resultSet.next()) {
            return resultSet.getString("email");
        }
        return null;
    }

    public boolean changePassword(String password) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE owner SET password = ? WHERE owner_id = 'O001'", password);
    }
}