package lk.ijse.poultryfarm.util;

import lk.ijse.poultryfarm.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T execute(String sql, Object... obj) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < obj.length; i++) {
            pst.setObject(i + 1, obj[i]);
        }

        if(sql.startsWith("SELECT") || sql.startsWith("select")){
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;

        } else {
            int i = pst.executeUpdate();
            boolean isSuccess = i > 0;
            return (T) (Boolean) isSuccess;
        }
    }
}
