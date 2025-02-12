package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO{
    String getNextId() throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    boolean save(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(String customerId) throws SQLException;
}
