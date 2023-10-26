package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Order;
import ua.nure.pharmacy.entity.OrderStatus;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderStatusRepository implements CRUDOperation<OrderStatus>, ResultSetExtractor<OrderStatus> {
    @Override
    public OrderStatus findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderStatusRepositorySQL.FIND_BY_ID_ORDER_STATUS)) {
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
    public List<OrderStatus> findAll() {
        List<OrderStatus> orders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderStatusRepositorySQL.SELECT_ALL_ORDER_STATUS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderStatus orderStatus = extractFromResultSet(resultSet);
                orders.add(orderStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean insert(OrderStatus orderStatus) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderStatusRepositorySQL.INSERT_ORDER_STATUS)) {
            statement.setInt(1, orderStatus.getId());
            statement.setString(2, orderStatus.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OrderStatus orderStatus) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderStatusRepositorySQL.UPDATE_ORDER_STATUS_BY_ID)) {
            statement.setInt(1,orderStatus.getId());
            statement.setString(2, orderStatus.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OrderStatus orderStatus) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderStatusRepositorySQL.DELETE_ORDER_STATUS)) {
            statement.setInt(1, orderStatus.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OrderStatus extractFromResultSet(ResultSet resultSet) throws SQLException {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(resultSet.getInt("id"));
        orderStatus.setName(resultSet.getString("name"));
        return orderStatus;
    }
}
