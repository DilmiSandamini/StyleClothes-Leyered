package lk.ijse.gdse72.styleclothesleyeredarchitecture.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Item {
    private String itemID;
    private String itemName;
    private BigDecimal price;
    private int quantity;
    private String categoryID;
}
