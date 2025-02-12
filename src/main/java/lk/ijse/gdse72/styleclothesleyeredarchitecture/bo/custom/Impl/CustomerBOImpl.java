package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.CustomerBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.CustomerDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Customer;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public String getNextCustomerId() throws SQLException {
        return customerDAO.getNextId();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCustomerID(), c.getName(), c.getEmail(), c.getContact(), c.getAddress(),c.getRequestItemsID()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.save(new Customer(
                customerDTO.getCustomerID(),
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getContact(),
                customerDTO.getAddress(),
                customerDTO.getRequestItemsID()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return customerDAO.update(new Customer(
                customerDTO.getCustomerID(),
                customerDTO.getName(),
                customerDTO.getEmail(),
                customerDTO.getContact(),
                customerDTO.getAddress(),
                customerDTO.getRequestItemsID()
        ));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }
}
