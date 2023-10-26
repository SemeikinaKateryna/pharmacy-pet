package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Emergency;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.EmergencyRepository;

import java.util.List;

@Service
public class EmergencyService implements CRUDOperation<Emergency> {
    EmergencyRepository emergencyRepository = new EmergencyRepository();
    @Override
    public Emergency findById(int id) {
        return emergencyRepository.findById(id);
    }

    @Override
    public List<Emergency> findAll() {
        return emergencyRepository.findAll();
    }

    @Override
    public boolean insert(Emergency emergency) {
        return emergencyRepository.insert(emergency);
    }

    @Override
    public boolean update(Emergency emergency) {
        return emergencyRepository.update(emergency);
    }

    @Override
    public boolean delete(Emergency emergency) {
        return emergencyRepository.delete(emergency);
    }

}
