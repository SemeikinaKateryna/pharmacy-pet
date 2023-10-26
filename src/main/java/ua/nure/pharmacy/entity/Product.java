package ua.nure.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String description;
    private Manufacturer manufacturer;
    private String photoUrl;
}