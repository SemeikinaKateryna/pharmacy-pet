package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.PackOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PackOrderRepository {
    OrderRepository orderRepository = new OrderRepository();
    PackRepository packRepository = new PackRepository();
    public List<PackOrder> findByIdOrderId (int order_id) {
        List<PackOrder> itemsByCart = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackOrderRepositorySQL.FIND_BY_ID_ORDER_ID)) {
            statement.setInt(1, order_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
               PackOrder packOrder = extractFromResultSet(resultSet);
               itemsByCart.add(packOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsByCart;
    }

    public List<PackOrder> findAll() {
        List<PackOrder> packOrders = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackOrderRepositorySQL.SELECT_ALL_PACK_ORDER)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PackOrder packOrder = extractFromResultSet(resultSet);
                packOrders.add(packOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packOrders;
    }

    public boolean insert(PackOrder packOrder) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackOrderRepositorySQL.INSERT_PACK_ORDER)) {
            statement.setInt(1, packOrder.getPack().getId());
            statement.setInt(2, packOrder.getOrder().getId());
            statement.setDouble(3, packOrder.getAmount());
            statement.setDouble(4, packOrder.getPrice());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(PackOrder packOrder) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackOrderRepositorySQL.UPDATE_PACK_ORDER)) {
            statement.setInt(1,packOrder.getAmount());
            statement.setDouble(2,packOrder.getPrice());
            statement.setInt(3, packOrder.getPack().getId());
            statement.setInt(4, packOrder.getOrder().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(PackOrder packOrder) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackOrderRepositorySQL.DELETE_PACK_ORDER)) {
            statement.setInt(1, packOrder.getPack().getId());
            statement.setInt(2, packOrder.getOrder().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public PackOrder extractFromResultSet(ResultSet resultSet) throws SQLException {
        PackOrder packOrder = new PackOrder();
        packOrder.setPack(packRepository.findById(resultSet.getInt("pack_id")));
        packOrder.setOrder(orderRepository.findById(resultSet.getInt("order_id")));
        packOrder.setAmount(resultSet.getInt("amount"));
        packOrder.setPrice(resultSet.getDouble("price"));
        return packOrder;
    }
}
