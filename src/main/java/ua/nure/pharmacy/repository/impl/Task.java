package ua.nure.pharmacy.repository.impl;

import lombok.Data;

@Data
public class Task {
    String name;
    Integer products_amount;
    Double total_sum;
}
