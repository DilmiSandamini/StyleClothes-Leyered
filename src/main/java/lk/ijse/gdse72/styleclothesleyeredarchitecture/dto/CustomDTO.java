package lk.ijse.gdse72.styleclothesleyeredarchitecture.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomDTO {
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
        //private String categoryID;
        private String categoryName;
        private String description;

        //Payment
        private String paymentID;
        private BigDecimal amount;
        private String paymentMethod;
        //private String date;
        //private String customerID;

        //Employee
        private String employeeID;
        //private String name;
        // private String contact;
        private String position;

        //Supplier
        private String supplierID;
        //    private String name;
        //    private String email;
        //    private String contact;

        //Ordrs
        private String orderId;
        private String customerId;
        private Date orderDate;

        //OrderDetails
        //private String orderId;
        private String itemId;
        //private int quantity;
        //private double price;

        //Returns
        private String returnID;
        //private String itemName;
        private BigDecimal itemPrice;
        private String reason;
        private String date;
        private String orderID;
    }

