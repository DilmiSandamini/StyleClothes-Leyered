package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.OrderDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select orderId from orders order by orderId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "O002"
            String substring = lastId.substring(1); // e.g., "002"
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i + 1; // 3
            return String.format("O%03d", newIdIndex);
        }
        return "O001";
    }

    @Override
    public boolean save(Orders entity) throws SQLException {
        return SQLUtil.execute(
                "insert into orders values (?,?,?)",
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getOrderDate()
        );
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean update(Orders entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }
}
