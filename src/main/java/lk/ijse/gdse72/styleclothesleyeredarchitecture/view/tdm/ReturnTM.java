package lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReturnTM {
    private String returnID;
    private String itemName;
    private BigDecimal itemPrice;
    private String reason;
    private String date;
    private String orderID;
}