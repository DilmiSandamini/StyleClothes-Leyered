package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.SupplierDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier> {
    ArrayList<String> getAllSupplierIds() throws SQLException;
}
