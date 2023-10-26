package ua.nure.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PackOrder {
    private Pack pack;
    private Order order;
    private Integer amount;
    private Double price;
}