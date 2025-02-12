package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.ItemBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.ItemDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException {
        ArrayList<Item> allEntityData = itemDAO.getAll();
        ArrayList<ItemDTO> allDTOData = new ArrayList<>();
        for (Item i : allEntityData) {
            allDTOData.add(new ItemDTO(
                    i.getItemID(),
                    i.getItemName(),
                    i.getPrice(),
                    i.getQuantity(),
                    i.getCategoryID()
            ));
        }
        return allDTOData;
    }

    @Override
    public boolean deleteItem(String itemId) throws SQLException {
        return itemDAO.delete(itemId);
    }

    @Override
    public String getNextItemId() throws SQLException {
        return itemDAO.getNextId();
    }

    @Override
    public boolean saveItem(ItemDTO itemDTO) throws SQLException {
        return itemDAO.save(new Item(
                itemDTO.getItemID(),
                itemDTO.getItemName(),
                itemDTO.getPrice(),
                itemDTO.getQuantity(),
                itemDTO.getCategoryID()
        ));
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException {
        return itemDAO.update(new Item(
                itemDTO.getItemID(),
                itemDTO.getItemName(),
                itemDTO.getPrice(),
                itemDTO.getQuantity(),
                itemDTO.getCategoryID()
        ));
    }
}
