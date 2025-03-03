package lk.ijse.gdse72.styleclothesleyeredarchitecture.dto;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.OrderDetails;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDTO implements Serializable {
    private String orderId;
    private String customerId;
    private Date orderDate;

    private ArrayList<OrderDetails> orderDetailsDTOS;
}
