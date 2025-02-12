package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.QueryDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Custom;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<Custom> searchOrder(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT orders.orderId,orders.orderDate,orders.customerID,orderdetails.orderId,orderdetails.itemId,orderdetails.quantity,orderdetails.price from Orders inner join OrderDetails on Orders.orderId=OrderDetails.orderId where Orders.orderId= ?" , orderId);
        ArrayList<Custom> allRecords = new ArrayList<>();

        while (rst.next()) {
            String ordersId = rst.getString("ordersId");
            Date orderDate = rst.getDate("orderDate");
            String customerId = rst.getString("customerId");
            String itemId = rst.getString("itemId");
            int quantity = rst.getInt("quantity");
            double price = rst.getDouble("price");

            Custom custom = new Custom(ordersId, orderDate, customerId, itemId, quantity, price);
            allRecords.add(custom);
        }
        return allRecords;
    }

}
