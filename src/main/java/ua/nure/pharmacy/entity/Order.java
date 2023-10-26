package ua.nure.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.pharmacy.repository.impl.PackRepository;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order {
    private Integer id;
    private Customer customer;
    private OrderStatus status;
    private Emergency emergency;
    private Double price;
}