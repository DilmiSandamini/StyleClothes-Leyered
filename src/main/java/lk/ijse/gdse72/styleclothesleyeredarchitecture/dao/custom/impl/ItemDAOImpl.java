package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.ItemDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Item;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.OrderDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("select itemID from item order by itemID desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst =SQLUtil.execute("select * from item");

        ArrayList<Item> itemS = new ArrayList<>();

        while (rst.next()) {
            Item item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getInt(4),
                    rst.getString(5)
            );
            itemS.add(item);
        }
        return itemS;
    }

    @Override
    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select itemID from item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

    @Override
    public String getItemNameById(String itemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select itemName from item where itemID=?", itemId);

        if (rst.next()) {
            return rst.getString("itemName");
        }
        return null;
    }

    @Override
    public ItemDTO findById(String selectedItemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from item where itemID=?", selectedItemId);

        if (rst.next()) {
            return new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getBigDecimal(3),
                    rst.getInt(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    @Override
    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute(
                "insert into item values (?,?,?,?,?)",
                entity.getItemID(),
                entity.getItemName(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCategoryID()
        );
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute(
                "update item set itemName=?, price=?, quantity=?, categoryID=? where itemID=?",
                entity.getItemName(),
                entity.getPrice(),
                entity.getQuantity(),
                entity.getCategoryID(),
                entity.getItemID()
        );
    }

    @Override
    public boolean delete(String itemId) throws SQLException {
        return SQLUtil.execute("delete from item where itemID=?", itemId);
    }

    public boolean reduceQty(OrderDetails orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "update item set quantity = quantity - ? where itemID = ?",
                orderDetailsDTO.getQuantity(),   // QYT to reduce
                orderDetailsDTO.getItemId()      // Item ID
        );
    }
}
