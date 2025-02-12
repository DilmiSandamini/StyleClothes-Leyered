package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDetailsDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    String getNextOrderId() throws SQLException;

    ArrayList<String> getAllItemIds() throws SQLException;

    ArrayList<String> getAllCustomerIds() throws SQLException;

    boolean saveOrderDetailsList(ArrayList<OrderDetails> orderDetailsS) throws SQLException;

    boolean saveOrder(OrderDTO orderDTO) throws SQLException;

    CustomerDTO findByCustomerId(String selectedCustomerId) throws SQLException;

    ItemDTO findByItemId(String selectedItemId) throws SQLException;
}
