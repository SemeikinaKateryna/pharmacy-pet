package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.PackOrder;
import ua.nure.pharmacy.repository.impl.PackOrderRepository;

import java.util.List;

@Service
public class PackOrderService {
    PackOrderRepository packOrderRepository = new PackOrderRepository();

    public List<PackOrder> findByOrderId(int id) {
        return packOrderRepository.findByIdOrderId(id);
    }

    public List<PackOrder> findAll() {
        return packOrderRepository.findAll();
    }

    public boolean insert(PackOrder packOrder) {
        return packOrderRepository.insert(packOrder);
    }

    public boolean update(PackOrder packOrder) {
        return packOrderRepository.update(packOrder);
    }

    public boolean delete(PackOrder packOrder) {
        return packOrderRepository.delete(packOrder);
    }
}
