package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDetailsDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public boolean save(OrderDetails entity) throws SQLException {
        return SQLUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }

    @Override
    public String getNextId() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
}
