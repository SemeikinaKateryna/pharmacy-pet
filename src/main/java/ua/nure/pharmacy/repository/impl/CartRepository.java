package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Cart;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartRepository implements CRUDOperation<Cart>, ResultSetExtractor<Cart> {
    CustomerRepository customerRepository = new CustomerRepository();

    @Override
    public Cart findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartRepositorySQL.FIND_BY_ID_CART)) {
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
    public List<Cart> findAll() {
        List<Cart> carts = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartRepositorySQL.SELECT_ALL_CART)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cart cart = extractFromResultSet(resultSet);
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    @Override
    public boolean insert(Cart cart) {
        if (cart.getPrice() == null) {
            return insertOnlyWithCustomer(cart);
        }
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartRepositorySQL.INSERT_CART)) {
            statement.setInt(1, cart.getCustomer().getId());
            statement.setDouble(2, cart.getPrice());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertOnlyWithCustomer(Cart cart) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartRepositorySQL.INSERT_CART_ONLY_CUSTOMER_iD)) {
            statement.setInt(1, cart.getCustomer().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Cart cart) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartRepositorySQL.UPDATE_CART)) {
            statement.setInt(1, cart.getCustomer().getId());
            statement.setDouble(2, cart.getPrice());
            statement.setInt(3, cart.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Cart cart) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartRepositorySQL.DELETE_CART)) {
            statement.setInt(1, cart.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Cart extractFromResultSet(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setId(resultSet.getInt("id"));
        cart.setCustomer(customerRepository.findById(resultSet.getInt("customer_id")));
        cart.setPrice(resultSet.getDouble("price"));
        return cart;
    }

    public Cart findByUserId(Integer userId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartRepositorySQL.FIND_BY_CUSTOMER_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
