package lk.ijse.gdse72.styleclothesleyeredarchitecture.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Return {
    private String returnID;
    private String itemName;
    private BigDecimal itemPrice;
    private String reason;
    private String date;
    private String orderID;
}
