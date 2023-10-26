package ua.nure.pharmacy.repository;

import java.util.List;

public interface CRUDOperation<T> {
    T findById(int id);
    List<T> findAll();
    boolean insert(T t);
    boolean update(T t);
    boolean delete(T t);
}