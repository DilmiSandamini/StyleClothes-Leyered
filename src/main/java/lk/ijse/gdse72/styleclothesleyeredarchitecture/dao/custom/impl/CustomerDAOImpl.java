package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.CustomerDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Customer;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT customerID FROM customer ORDER BY customerID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }
    @Override
    public String getCustomerNameById(String customerId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select name from customer where customerID=?", customerId);

        if (rst.next()) {
            return rst.getString("name");
        }
        return null;
    }
    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from customer");

        ArrayList<Customer> allCustomers = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            allCustomers.add(customer);
        }
        return allCustomers;
    }
    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute(
                "insert into customer values (?,?,?,?,?,?)",
                entity.getCustomerID(),
                entity.getName(),
                entity.getEmail(),
                entity.getContact(),
                entity.getAddress(),
                entity.getRequestItemsID()
        );
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE customer SET name = ?, email = ?, contact = ?, address = ?, requestItemsID = ? WHERE customerID = ?",
                entity.getName(),
                entity.getEmail(),
                entity.getContact(),
                entity.getAddress(),
                entity.getRequestItemsID(),
                entity.getCustomerID()
        );
    }
    @Override
    public boolean delete(String customerId) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE customerID = ?", customerId);
    }
    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT customerID FROM customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }
    @Override
    public CustomerDTO findById(String customerId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE customerID = ?", customerId);

        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }
        return null;
    }
}
