package lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItemTM {
    private String itemID;
    private String itemName;
    private BigDecimal price;
    private int quantity;
    private String categoryID;
}
