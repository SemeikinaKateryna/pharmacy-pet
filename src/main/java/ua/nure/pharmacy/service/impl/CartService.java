package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Cart;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.CartRepository;

import java.util.List;

@Service
public class CartService implements CRUDOperation<Cart> {
    CartRepository cartRepository = new CartRepository();

    @Override
    public Cart findById(int id) {
        return cartRepository.findById(id);
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public boolean insert(Cart cart) {
        return cartRepository.insert(cart);
    }
    @Override
    public boolean update(Cart cart) {
        return cartRepository.update(cart);
    }
    @Override
    public boolean delete(Cart cart) {
        return cartRepository.delete(cart);
    }

    public Cart findByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }
}
