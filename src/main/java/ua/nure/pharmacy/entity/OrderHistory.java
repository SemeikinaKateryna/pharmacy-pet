package ua.nure.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.jdbc.Work;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderHistory {
    private Integer id;
    private LocalDate date;
    private Order order;
    private Worker worker;
    private OrderStatus status;
}