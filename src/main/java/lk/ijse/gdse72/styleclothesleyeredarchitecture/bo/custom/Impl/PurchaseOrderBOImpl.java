package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.CustomerDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.ItemDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.OrderDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.db.DBConnection;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDetailsDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.OrderDetails;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO{
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    public String getNextOrderId() throws SQLException{
        return orderDAO.getNextId();
    }

    public ArrayList <String> getAllItemIds() throws SQLException{
        return itemDAO.getAllItemIds();
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException{
        return customerDAO.getAllCustomerIds();
    }

    public CustomerDTO findByCustomerId(String selectedCustomerId) throws SQLException{
        return customerDAO.findById(selectedCustomerId);
    }

    public ItemDTO findByItemId(String selectedItemId) throws SQLException{
        return itemDAO.findById(selectedItemId);
    }

    @Override
    public boolean saveOrderDetailsList(ArrayList<OrderDetails> orderDetailsS) throws SQLException {
        for (OrderDetails orderDetails : orderDetailsS) {
            boolean isOrderDetailsSaved = orderDetailsDAO.save(orderDetails);
            if (!isOrderDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = itemDAO.reduceQty(orderDetails);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderDAO.save(new Orders(orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getOrderDate()));
            if (isOrderSaved) {
                boolean isOrderDetailListSaved = saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
