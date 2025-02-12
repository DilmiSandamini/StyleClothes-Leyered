package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    boolean deleteItem(String itemId) throws SQLException;
    String getNextItemId() throws SQLException;
    ArrayList<ItemDTO> getAllItems() throws SQLException;
    boolean saveItem(ItemDTO itemDTO) throws SQLException;
    boolean updateItem(ItemDTO itemDTO) throws SQLException;
}
