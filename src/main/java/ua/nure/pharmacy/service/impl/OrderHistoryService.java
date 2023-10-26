package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.OrderHistory;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.OrderHistoryRepository;

import java.util.List;

@Service
public class OrderHistoryService implements CRUDOperation<OrderHistory> {
    OrderHistoryRepository orderHistoryRepository = new OrderHistoryRepository();

    @Override
    public OrderHistory findById(int id) {
        return orderHistoryRepository.findById(id);
    }

    @Override
    public List<OrderHistory> findAll() {
        return orderHistoryRepository.findAll();
    }

    @Override
    public boolean insert(OrderHistory orderHistory) {
        return orderHistoryRepository.insert(orderHistory);
    }

    @Override
    public boolean update(OrderHistory orderHistory) {
        return orderHistoryRepository.update(orderHistory);
    }

    @Override
    public boolean delete(OrderHistory orderHistory) {
        return orderHistoryRepository.delete(orderHistory);
    }

    public List<OrderHistory> findByOrderId(int order_id){
        return orderHistoryRepository.findByOrderId(order_id);
    }
}
