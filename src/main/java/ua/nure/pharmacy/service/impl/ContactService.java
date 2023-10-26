package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Contact;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.ContactRepository;

import java.util.List;

@Service
public class ContactService implements CRUDOperation<Contact> {
    ContactRepository contactRepository = new ContactRepository();

    @Override
    public Contact findById(int id) {
        return contactRepository.findById(id);
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public boolean insert(Contact contact) {
        return contactRepository.insert(contact);
    }

    @Override
    public boolean update(Contact contact) {
        return contactRepository.update(contact);
    }

    @Override
    public boolean delete(Contact contact) {
        return contactRepository.delete(contact);
    }

    public List<Contact> findByIdOrderId(Integer id) {
        return contactRepository.findByIdOrderId(id);
    }
}
