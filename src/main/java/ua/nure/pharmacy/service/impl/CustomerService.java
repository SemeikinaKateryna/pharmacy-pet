package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Customer;
import ua.nure.pharmacy.entity.User;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.impl.CustomerRepository;
import ua.nure.pharmacy.service.SQLRequest;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.service.sorter.CustomerSorter;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.*;
import java.util.List;

@Service
public class CustomerService implements CRUDOperation<Customer>,SQLRequest<Customer> {
    private final CustomerRepository customerRepository = new CustomerRepository();
    private final CustomerSorter customerSorter = new CustomerSorter();


    @Override
    public Customer findByLogin(String login) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CustomerServiceSQL.FIND_BY_CUSTOMER_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return customerRepository.extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id);
    }
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    @Override
    public boolean insert(Customer customer) {
        return customerRepository.insert(customer);
    }
    @Override
    public boolean update(Customer customer) {
        return customerRepository.update(customer);
    }
    @Override
    public boolean delete(Customer customer) {
        return customerRepository.delete(customer);
    }

    public List<Customer> findAllSortedByNameAscending() {
        List<Customer> customers = customerRepository.findAll();
        return customerSorter.sortByNameAscending(customers);
    }

    public List<Customer> findAllSortedByNameDescending() {
        List<Customer> customers = customerRepository.findAll();
        return customerSorter.sortByNameDescending(customers);
    }

    public boolean checkEmailExists(String email){
        List<Customer> customers = findAll();
        boolean emailFound = false;
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                emailFound = true;
                break;
            }
        }
        return emailFound;
    }

    public boolean checkPhoneExists(String phone){
        List<Customer> customers = findAll();
        boolean phoneFound = false;
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phone)) {
                phoneFound = true;
                break;
            }
        }
        return phoneFound;
    }

    public boolean checkLoginExists(String login){
        List<Customer> customers = findAll();
        boolean loginFound = false;
        for (Customer customer : customers) {
            if (customer.getLogin().equals(login)) {
                loginFound = true;
                break;
            }
        }
        return loginFound;
    }
}