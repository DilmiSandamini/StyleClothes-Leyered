package lk.ijse.gdse72.styleclothesleyeredarchitecture.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDTO {
    private String paymentID;
    private BigDecimal amount;
    private String paymentMethod;
    private String date;
    private String customerID;
}
