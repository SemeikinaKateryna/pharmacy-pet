package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Order;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository  implements CRUDOperation<Order>, ResultSetExtractor<Order> {
    CustomerRepository customerRepository = new CustomerRepository();
    OrderStatusRepository orderStatusRepository = new OrderStatusRepository();
    EmergencyRepository emergencyRepository = new EmergencyRepository();

    @Override
    public Order findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.FIND_BY_ID_ORDER)) {
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
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.SELECT_ALL_ORDER)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = extractFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean insert(Order order) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.INSERT_ORDER)) {
            statement.setInt(1, order.getCustomer().getId());
            statement.setInt(2, order.getStatus().getId());
            statement.setInt(3, order.getEmergency().getId());
            statement.setDouble(4, order.getPrice());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Order order) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.UPDATE_ORDER_BY_ID)) {
            statement.setInt(1, order.getCustomer().getId());
            statement.setInt(2, order.getStatus().getId());
            statement.setInt(3, order.getEmergency().getId());
            statement.setDouble(4, order.getPrice());
            statement.setInt(5, order.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Order order) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.DELETE_ORDER)) {
            statement.setInt(1, order.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Order extractFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setCustomer(customerRepository.findById(resultSet.getInt("customer_id")));
        order.setStatus(orderStatusRepository.findById(resultSet.getInt("status_id")));
        order.setEmergency(emergencyRepository.findById(resultSet.getInt("emergency_id")));
        order.setPrice(resultSet.getDouble("price"));
        return order;
    }

    public Order findLast() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.FIND_LAST)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> findByIdCustomerId(Integer id) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.FIND_BY_ID_ORDER_CUSTOMER_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = extractFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> findByStatusId(int id) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.FIND_BY_ID_ORDER_STATUS_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = extractFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> findByStatusIdAndOrderByEmergency(int id) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderRepositorySQL.FIND_BY_STATUS_ID_ORDER_BY_EMERGENCY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = extractFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
