package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Manufacturer;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.ManufacturerRepository;

import java.util.List;

@Service
public class ManufacturerService implements CRUDOperation<Manufacturer> {
    ManufacturerRepository manufacturerRepository = new ManufacturerRepository();

    @Override
    public Manufacturer findById(int id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public boolean insert(Manufacturer manufacturer) {
        return manufacturerRepository.insert(manufacturer);
    }
    @Override
    public boolean update(Manufacturer manufacturer) {
        return manufacturerRepository.update(manufacturer);
    }
    @Override
    public boolean delete(Manufacturer manufacturer) {
        return manufacturerRepository.delete(manufacturer);
    }

    public Manufacturer findByName(String parameterFinal) {
        return manufacturerRepository.findByName(parameterFinal);
    }
}
