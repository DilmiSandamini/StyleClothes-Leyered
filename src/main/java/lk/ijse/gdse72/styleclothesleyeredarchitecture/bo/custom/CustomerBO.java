package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    String getNextCustomerId() throws SQLException;
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException;
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException;
    boolean deleteCustomer(String customerId) throws SQLException;
}
