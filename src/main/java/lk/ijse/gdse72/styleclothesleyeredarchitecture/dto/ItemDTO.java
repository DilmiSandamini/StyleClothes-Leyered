package lk.ijse.gdse72.styleclothesleyeredarchitecture.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ItemDTO {
    private String itemID;
    private String itemName;
    private BigDecimal price;
    private int quantity;
    private String categoryID;
}
