package ua.nure.pharmacy.entity;

import lombok.Data;

@Data
public class CartPack {
    Cart cart;
    Pack pack;
    Integer amount;
}
