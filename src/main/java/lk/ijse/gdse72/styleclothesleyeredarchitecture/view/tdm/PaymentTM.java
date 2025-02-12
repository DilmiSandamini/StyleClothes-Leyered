package lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaymentTM {
    private String paymentID;
    private BigDecimal amount;
    private String paymentMethod;
    private String date;
    private String customerID;
}
