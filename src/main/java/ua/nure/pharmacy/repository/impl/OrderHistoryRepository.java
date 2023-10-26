package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.OrderHistory;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderHistoryRepository implements CRUDOperation<OrderHistory>,
        ResultSetExtractor<OrderHistory> {
    OrderRepository orderRepository = new OrderRepository();
    WorkerRepository workerRepository = new WorkerRepository();
    OrderStatusRepository orderStatusRepository = new OrderStatusRepository();

    @Override
    public OrderHistory findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderHistoryRepositorySQL.FIND_BY_ID_ORDER_HISTORY)) {
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
    public List<OrderHistory> findAll() {
        List<OrderHistory> orderHistories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderHistoryRepositorySQL.SELECT_ALL_ORDER_HISTORY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderHistory orderHistory = extractFromResultSet(resultSet);
                orderHistories.add(orderHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderHistories;
    }

    @Override
    public boolean insert(OrderHistory orderHistory) {
        if (orderHistory.getWorker() != null) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement
                         (SQLQuery.OrderHistoryRepositorySQL.INSERT_ORDER_HISTORY)) {
                statement.setDate(1, Date.valueOf(orderHistory.getDate()));
                statement.setInt(2, orderHistory.getOrder().getId());
                statement.setDouble(3, orderHistory.getWorker().getId());
                statement.setInt(4, orderHistory.getStatus().getId());
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return insertWithoutWorker(orderHistory);
        }
    }

    private boolean insertWithoutWorker(OrderHistory orderHistory) {
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement
                (SQLQuery.OrderHistoryRepositorySQL.INSERT_ORDER_HISTORY_WITHOUT_WORKER)) {
            statement.setDate(1, Date.valueOf(orderHistory.getDate()));
            statement.setInt(2, orderHistory.getOrder().getId());
            statement.setInt(3, orderHistory.getStatus().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OrderHistory orderHistory) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderHistoryRepositorySQL.UPDATE_ORDER_HISTORY_BY_ID)) {
            statement.setDate(1, Date.valueOf(orderHistory.getDate()));
            statement.setInt(2, orderHistory.getOrder().getId());
            statement.setDouble(3, orderHistory.getWorker().getId());
            statement.setInt(4, orderHistory.getStatus().getId());
            statement.setInt(5, orderHistory.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OrderHistory orderHistory) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderHistoryRepositorySQL.DELETE_ORDER_HISTORY)) {
            statement.setInt(1, orderHistory.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OrderHistory extractFromResultSet(ResultSet resultSet) throws SQLException {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(resultSet.getInt("id"));
        orderHistory.setStatus(orderStatusRepository.findById(resultSet.getInt("status_id")));
        orderHistory.setOrder(orderRepository.findById(resultSet.getInt("order_id")));
        orderHistory.setWorker(workerRepository.findById(resultSet.getInt("worker_id")));
        orderHistory.setDate((resultSet.getDate("date")).toLocalDate());
        return orderHistory;
    }

    public List<OrderHistory> findByOrderId(int order_id) {
        List<OrderHistory> orderHistories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.OrderHistoryRepositorySQL.FIND_BY_ID_ORDER_ID_ORDER_HISTORY)) {
            statement.setInt(1, order_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderHistory orderHistory = extractFromResultSet(resultSet);
                orderHistories.add(orderHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderHistories;
    }
}
