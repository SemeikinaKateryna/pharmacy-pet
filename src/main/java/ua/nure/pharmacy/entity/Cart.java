package ua.nure.pharmacy.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cart {
    Integer id;
    Customer customer;
    Double price;

    public Cart() {
    }
    public Cart(Customer customer) {
        this.customer = customer;
    }
    public Cart(Integer id, Customer customer, Double price) {
        this.id = id;
        this.customer = customer;
        this.price = price;
    }
}
