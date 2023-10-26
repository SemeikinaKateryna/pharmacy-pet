package ua.nure.pharmacy.entity;

import lombok.Data;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class Pack {
    private Integer id;
    private Integer amount;
    private Double price;
    private Product product;
    private LocalDate expirationDate;
    private LocalDate manufactureDate;
    private Dose dose;
    private Integer packsAmount;
}