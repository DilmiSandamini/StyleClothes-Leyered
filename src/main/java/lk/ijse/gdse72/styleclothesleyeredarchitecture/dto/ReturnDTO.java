package lk.ijse.gdse72.styleclothesleyeredarchitecture.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ReturnDTO {
    private String returnID;
    private String itemName;
    private BigDecimal itemPrice;
    private String reason;
    private String date;
    private String orderID;
}
