package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.OrderStatus;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.OrderStatusRepository;

import java.util.List;

@Service
public class OrderStatusService implements CRUDOperation<OrderStatus> {
    OrderStatusRepository orderStatusRepository;
    public OrderStatusService(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderStatus findById(int id) {
        return orderStatusRepository.findById(id);
    }

    @Override
    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAll();
    }

    @Override
    public boolean insert(OrderStatus orderStatus) {
        return orderStatusRepository.insert(orderStatus);
    }

    @Override
    public boolean update(OrderStatus orderStatus) {
        return orderStatusRepository.update(orderStatus);
    }

    @Override
    public boolean delete(OrderStatus orderStatus) {
        return orderStatusRepository.delete(orderStatus);
    }
}
