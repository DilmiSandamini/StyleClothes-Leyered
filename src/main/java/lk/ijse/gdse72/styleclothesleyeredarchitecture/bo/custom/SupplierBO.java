package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    String getNextSupplierId() throws SQLException;
    ArrayList<SupplierDTO> getAllSuppliers() throws SQLException;
    boolean deleteSupplier(String supplierId) throws SQLException;
    boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException;
    boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException;

}
