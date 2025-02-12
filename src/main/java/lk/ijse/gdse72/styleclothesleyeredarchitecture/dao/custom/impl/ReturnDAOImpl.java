package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.ReturnDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Return;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnDAOImpl implements ReturnDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT returnID FROM returns ORDER BY returnID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("R%03d", newIdIndex);
        }
        return "R001";
    }

    @Override
    public ArrayList<Return> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from returns");

        ArrayList<Return> returnS = new ArrayList<>();

        while (rst.next()) {
            Return returns = new Return(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
            returnS.add(returns);
        }
        return returnS;
    }

    @Override
    public boolean save(Return entity) throws SQLException {
        return SQLUtil.execute(
                "insert into returns values (?,?,?,?,?,?)",
                entity.getReturnID(),
                entity.getItemName(),
                entity.getItemPrice(),
                entity.getReason(),
                entity.getDate(),
                entity.getOrderID()
        );
    }

    @Override
    public boolean update(Return entity) throws SQLException {
        return SQLUtil.execute(
                "update returns set itemName=?, itemPrice=?, reason=?, date=?, orderId=? where returnID=?",
                entity.getItemName(),
                entity.getItemPrice(),
                entity.getReason(),
                entity.getDate(),
                entity.getOrderID(),
                entity.getReturnID()
        );
    }

    @Override
    public boolean delete(String returnID) throws SQLException {
        return SQLUtil.execute("delete from returns where returnID=?", returnID);
    }

}
