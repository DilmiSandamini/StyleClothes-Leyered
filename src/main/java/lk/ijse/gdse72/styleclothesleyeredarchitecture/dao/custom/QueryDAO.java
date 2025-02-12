package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SuperDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<Custom> searchOrder(String orderId) throws SQLException, ClassNotFoundException;
    }
