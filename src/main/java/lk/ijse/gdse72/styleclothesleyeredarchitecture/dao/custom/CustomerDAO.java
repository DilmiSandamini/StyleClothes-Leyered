package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Customer;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
    ArrayList<String> getAllCustomerIds() throws SQLException;
    CustomerDTO findById(String customerId) throws SQLException;
    String getCustomerNameById(String customerId) throws SQLException;
}
