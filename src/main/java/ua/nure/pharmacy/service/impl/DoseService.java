package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Dose;
import ua.nure.pharmacy.repository.impl.DoseRepository;

@Service
public class DoseService {
    DoseRepository doseRepository;
    public Dose findById(int dose_id){
        return doseRepository.findById(dose_id);
    }
}
