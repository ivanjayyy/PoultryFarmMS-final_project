package lk.ijse.poultryfarm.model;


import lk.ijse.poultryfarm.dto.OwnerDto;
import lk.ijse.poultryfarm.util.CrudUtil;

import java.sql.SQLException;

public class OwnerModel {

    public boolean saveOwner(OwnerDto ownerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO owner VALUES (?,?,?,?,?)", ownerDto.getOwnerId(),ownerDto.getName(),ownerDto.getUsername(),ownerDto.getPassword(),ownerDto.getEmail());
    }

    public boolean updateOwner(OwnerDto ownerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE owner SET name = ?, username = ?, password = ?, email - ? WHERE owner_id = ?", ownerDto.getName(),ownerDto.getUsername(),ownerDto.getPassword(),ownerDto.getEmail(),ownerDto.getOwnerId());
    }

}
