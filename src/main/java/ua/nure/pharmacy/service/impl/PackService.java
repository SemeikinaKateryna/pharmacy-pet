package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Pack;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.PackRepository;

import java.util.List;

@Service
public class PackService implements CRUDOperation<Pack> {
    PackRepository packRepository = new PackRepository();
    @Override
    public Pack findById(int id) {
        return packRepository.findById(id);
    }

    @Override
    public List<Pack> findAll() {
        return packRepository.findAll();
    }

    @Override
    public boolean insert(Pack pack) {
        return packRepository.insert(pack);
    }
    @Override
    public boolean update(Pack pack) {
        return packRepository.update(pack);
    }
    @Override
    public boolean delete(Pack pack) {
        return packRepository.delete(pack);
    }

    public List<Pack> findByProductId(Integer id) {
        return packRepository.findByProductId(id);
    }
}
