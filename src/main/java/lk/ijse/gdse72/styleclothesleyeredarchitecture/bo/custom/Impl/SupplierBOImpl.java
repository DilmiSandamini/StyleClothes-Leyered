package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.SupplierBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.SupplierDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.SupplierDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException{
        return supplierDAO.save(new Supplier(
                supplierDTO.getSupplierID(),
                supplierDTO.getName(),
                supplierDTO.getEmail(),
                supplierDTO.getContact()

        ));
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException{
        return supplierDAO.update(new Supplier(
                supplierDTO.getSupplierID(),
                supplierDTO.getName(),
                supplierDTO.getEmail(),
                supplierDTO.getContact()
        ));
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException{
        return supplierDAO.delete(supplierId);
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException{
        ArrayList<SupplierDTO> allSuppliers = new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();

        for (Supplier s : all) {
            allSuppliers.add(new SupplierDTO(
                    s.getSupplierID(),
                    s.getName(),
                    s.getEmail(),
                    s.getContact()
            ));
        }
        return allSuppliers;
    }

    @Override
    public String getNextSupplierId() throws SQLException{
        return supplierDAO.getNextId();
    }

}
