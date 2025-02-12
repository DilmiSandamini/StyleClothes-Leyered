package lk.ijse.gdse72.styleclothesleyeredarchitecture.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String customerID;
    private String name;
    private String email;
    private String contact;
    private String address;
    private String requestItemsID;
}
