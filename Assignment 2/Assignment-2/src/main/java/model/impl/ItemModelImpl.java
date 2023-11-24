package model.impl;

import DB.DBConnection;
import dto.ItemDto;
import model.ItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemDto dto) {
        String sql="INSERT INTO Item VALUES(?,?,?,?)";
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getCode());
            pstm.setString(2,dto.getDescription());
            pstm.setDouble(3,(dto.getUnitPrice()));
            pstm.setInt(4, (dto.getQty()));
            int result = pstm.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateItem(ItemDto dto) {
        String sql = "UPDATE Item SET description=? , unitPrice=? , qtyOnHand=? WHERE code=?";
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getDescription());
            pstm.setString(2, String.valueOf(dto.getUnitPrice()));
            pstm.setString(3, String.valueOf(dto.getQty()));
            pstm.setString(4, (dto.getCode()));
            int result = pstm.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }




    @Override
    public boolean deleteItem(String id) {
        String sql = "DELETE FROM Item WHERE code=?";
        try {
            PreparedStatement  pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,id);
            int result = pstm.executeUpdate();
            return true;


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public List<ItemDto> allItem() {
        List<ItemDto> itemList=new ArrayList<>();
        String sql ="SELECT * FROM Item";
        try {
            PreparedStatement  pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet result = pstm.executeQuery();
            while(result.next()){
                ItemDto d = new ItemDto(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4)
                );
                itemList.add(d);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return itemList;
    }
}
