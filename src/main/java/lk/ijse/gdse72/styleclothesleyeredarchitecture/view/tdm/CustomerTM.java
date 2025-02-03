package lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerTM {
    private String customerID;
    private String name;
    private String email;
    private String contact;
    private String address;
    private String requestItemsID;
}