package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Customer;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

@Repository
public class CustomerRepository implements CRUDOperation<Customer>, ResultSetExtractor<Customer> {

    RoleRepository roleRepository = new RoleRepository();


    @Override
    public Customer findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CustomerRepositorySQL.FIND_BY_ID_CUSTOMER)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CustomerRepositorySQL.SELECT_ALL_CUSTOMER)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = extractFromResultSet(resultSet);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public boolean insert(Customer customer) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CustomerRepositorySQL.INSERT_CUSTOMER)) {
            statement.setInt(1, customer.getAge());
            statement.setString(2, customer.getEmail());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Customer customer) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CustomerRepositorySQL.UPDATE_CUSTOMER_BY_ID)) {
            statement.setString(1, customer.getLogin());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getAddress());
            statement.setInt(4, customer.getAge());
            statement.setString(5, customer.getEmail());
            statement.setString(6, customer.getPhone());
            statement.setInt(7, customer.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Customer customer) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CustomerRepositorySQL.DELETE_CUSTOMER)) {
            statement.setInt(1, customer.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Customer extractFromResultSet(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("id"));
        customer.setLogin(resultSet.getString("login"));
        customer.setPassword(resultSet.getString("password"));
        customer.setRole(roleRepository.findById(resultSet.getInt("role_id")));
        customer.setName(resultSet.getString("name"));
        customer.setAddress(resultSet.getString("address"));
        customer.setRegisterDate(resultSet.getDate("register_date").toLocalDate());
        customer.setAge(resultSet.getInt("age"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhone(resultSet.getString("phone"));
        return customer;
    }
}
