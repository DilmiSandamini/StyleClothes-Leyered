package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.CrudDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Item;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
    ArrayList<String> getAllItemIds() throws SQLException;
    String getItemNameById(String itemId) throws SQLException;
    ItemDTO findById(String selectedItemId) throws SQLException;
    boolean reduceQty(OrderDetails orderDetailsDTO) throws SQLException;
}
