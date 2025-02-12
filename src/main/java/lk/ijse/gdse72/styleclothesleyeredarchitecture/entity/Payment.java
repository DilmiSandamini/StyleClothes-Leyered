package lk.ijse.gdse72.styleclothesleyeredarchitecture.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment {
    private String paymentID;
    private BigDecimal amount;
    private String paymentMethod;
    private String date;
    private String customerID;
}
