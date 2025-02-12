package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.SupplierDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.SupplierDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT supplierID FROM supplier ORDER BY supplierID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            int newIdIndex = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("S%03d", newIdIndex);
        }
        return "S001";
    }

    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM supplier");
        ArrayList<Supplier> supplierList = new ArrayList<>();

        while (rst.next()) {
            supplierList.add(new Supplier(
                    rst.getString("supplierID"),
                    rst.getString("name"),
                    rst.getString("email"),
                    rst.getString("contact")
            ));
        }
        return supplierList;
    }

    @Override
    public boolean save(Supplier entity) throws SQLException {
        return SQLUtil.execute(
                "INSERT INTO supplier VALUES (?, ?, ?, ?)",
                entity.getSupplierID(),
                entity.getName(),
                entity.getEmail(),
                entity.getContact()
        );
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute(
                "UPDATE supplier SET name=?, email=?, contact=? WHERE supplierID=?",
                entity.getName(),
                entity.getEmail(),
                entity.getContact(),
                entity.getSupplierID()
        );
    }

    @Override
    public boolean delete(String supplierId) throws SQLException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supplierID=?", supplierId);
    }

    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT supplierID FROM supplier");
        ArrayList<String> supplierIds = new ArrayList<>();
        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }
        return supplierIds;
    }
}
