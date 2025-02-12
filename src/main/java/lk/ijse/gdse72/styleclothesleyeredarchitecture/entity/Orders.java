package lk.ijse.gdse72.styleclothesleyeredarchitecture.entity;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDetailsDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Orders {
    private String orderId;
    private String customerId;
    private Date orderDate;

    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;

    public Orders(String orderId, String customerId, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }
}
