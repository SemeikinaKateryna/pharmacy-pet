package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Category;
import ua.nure.pharmacy.entity.Contact;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepository implements CRUDOperation<Contact>, ResultSetExtractor<Contact> {
    OrderRepository orderRepository = new OrderRepository();

    @Override
    public Contact findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ContactRepositorySQL.FIND_BY_ID_CONTACT)) {
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
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ContactRepositorySQL.SELECT_ALL_CONTACT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact contact = extractFromResultSet(resultSet);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    @Override
    public boolean insert(Contact contact) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ContactRepositorySQL.INSERT_CONTACT)) {
            statement.setInt(1, contact.getOrder().getId());
            statement.setString(2, contact.getName());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getPhone());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Contact contact) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ContactRepositorySQL.UPDATE_CONTACT_BY_ID)) {

            statement.setString(1, contact.getName());
            statement.setString(2, contact.getEmail());
            statement.setString(3, contact.getPhone());
            statement.setObject(4, contact.getOrder());
            statement.setInt(5, contact.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Contact contact) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ContactRepositorySQL.DELETE_CONTACT)) {
            statement.setInt(1, contact.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Contact extractFromResultSet(ResultSet resultSet) throws SQLException {
        Contact contact = new Contact();
        contact.setId(resultSet.getInt("id"));
        contact.setName(resultSet.getString("name"));
        contact.setOrder(orderRepository.findById(resultSet.getInt("order_id")));
        contact.setPhone(resultSet.getString("phone"));
        contact.setEmail(resultSet.getString("email"));
        return contact;
    }


    public List<Contact> findByIdOrderId(Integer id) {
        List<Contact> contacts = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ContactRepositorySQL.FIND_BY_ORDER_ID_CONTACT)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Contact contact = extractFromResultSet(resultSet);
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;

    }
}
