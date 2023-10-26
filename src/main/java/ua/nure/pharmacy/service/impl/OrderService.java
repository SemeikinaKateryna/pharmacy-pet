package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Contact;
import ua.nure.pharmacy.entity.Order;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.OrderRepository;

import java.util.List;

@Service
public class OrderService implements CRUDOperation<Order>{

    private final OrderRepository orderRepository = new OrderRepository();

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public boolean insert(Order order) {
        return orderRepository.insert(order);
    }

    @Override
    public boolean update(Order order) {
        return orderRepository.update(order);
    }

    @Override
    public boolean delete(Order order) {
        return orderRepository.delete(order);
    }

    public Order findLast() {
        return orderRepository.findLast();
    }

    public List<Order> findByIdCustomerId(Integer id) {
        return orderRepository.findByIdCustomerId(id);
    }

    public List<Order> findByStatusId(int id) {
        return orderRepository.findByStatusId(id);
    }

    public List<Order> findByStatusIdAndOrderByEmergency(int id) {
        return orderRepository.findByStatusIdAndOrderByEmergency(id);

    }
}
