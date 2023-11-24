package model.impl;

import DB.DBConnection;
import dto.CustomerDto;
import javafx.scene.control.Alert;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomerDto dto) {
        String sql = "INSERT INTO Customer VALUES(?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getId());
            pstm.setString(2,dto.getName());
            pstm.setString(3,dto.getAddress());
            pstm.setDouble(4,dto.getSalary());

            int rst = pstm.executeUpdate();
                return true;

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) {
        String sql = "UPDATE Customer SET name=? , address=? , salary=?  WHERE id=?";
        try {
            PreparedStatement  pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1,dto.getName());
            pstm.setString(2,dto.getAddress());
            pstm.setString(3, String.valueOf(dto.getSalary()));
            pstm.setString(4, dto.getId());
            int result = pstm.executeUpdate();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String id) {
        String sql = "DELETE FROM customer WHERE id=?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,id);
            int result = pstm.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public List<CustomerDto> allCustomer() throws SQLException, ClassNotFoundException {
        List<CustomerDto> list =new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();
        while(result.next()){
            CustomerDto c = new CustomerDto(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)

            );
            list.add(c);
        }
        return list;
    }


}
