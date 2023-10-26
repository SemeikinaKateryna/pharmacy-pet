package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.*;
import ua.nure.pharmacy.repository.impl.CartPackRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class CartPackService {
   CartPackRepository cartProductRepository = new CartPackRepository();
    public List<CartPack> findByIdCartId(int id) {
        return cartProductRepository.findByIdCartId(id);
    }

    public List<CartPack> findAll() {
        return cartProductRepository.findAll();
    }

    public void insert(CartPack cartProduct) {
        cartProductRepository.insert(cartProduct);
    }

    public void update(CartPack cartProduct) {
        cartProductRepository.update(cartProduct);
    }

    public void delete(CartPack cartProduct) {
        cartProductRepository.delete(cartProduct);
    }

    public CartPack findByIdCartIdAndPackId(Integer pack_id, Integer cart_id) {
        return cartProductRepository.findByIdCartIdAndPackId(pack_id, cart_id);
    }
}
