package model;

import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    public boolean saveCustomer(CustomerDto dto);
    public boolean updateCustomer(CustomerDto dto);
    public boolean deleteCustomer(String id);
    public List<CustomerDto> allCustomer() throws SQLException, ClassNotFoundException;

}
