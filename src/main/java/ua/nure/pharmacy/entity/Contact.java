package ua.nure.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Contact {
    private Integer id;
    private Order order;
    private String name;
    private String email;
    private String phone;
}