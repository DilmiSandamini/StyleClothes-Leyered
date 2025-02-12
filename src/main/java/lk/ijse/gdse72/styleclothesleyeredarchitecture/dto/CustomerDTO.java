package lk.ijse.gdse72.styleclothesleyeredarchitecture.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDTO {
    private String customerID;
    private String name;
    private String email;
    private String contact;
    private String address;
    private String requestItemsID;
}
