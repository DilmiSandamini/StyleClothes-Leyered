package lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsTM {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
