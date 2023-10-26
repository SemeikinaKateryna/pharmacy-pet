package ua.nure.pharmacy.service;

public interface SQLRequest<T> {
    T findByLogin(String login);
}