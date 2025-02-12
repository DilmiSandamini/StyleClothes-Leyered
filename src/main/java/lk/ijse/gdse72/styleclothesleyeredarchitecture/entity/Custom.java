package lk.ijse.gdse72.styleclothesleyeredarchitecture.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Custom {
    //Customer
    private String customerID;
    private String name;
    private String email;
    private String contact;
    private String address;
    private String requestItemsID;

    //Item
    private String itemID;
    private String itemName;
    private double price;
    private int quantity;
    private String categoryID;

    //Category
    private String categoryName;
    private String description;

    //Payment
    private String paymentID;
    private BigDecimal amount;
    private String paymentMethod;

    //Employee
    private String employeeID;
    private String position;

    //Supplier
    private String supplierID;

    //Returns
    private String returnID;
    private BigDecimal itemPrice;
    private String reason;
    private String date;
    private String orderID;

    //Orders
    private String orderId;
    private String customerId;
    private Date orderDate;

    //OrderDetails
    private String itemId;



    public Custom(String ordersId, Date orderDate, String customerId, String itemId, int quantity, double price) {
        this.orderId = ordersId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }
}
