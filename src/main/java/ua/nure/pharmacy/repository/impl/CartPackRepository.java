package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartPackRepository {
    CartRepository cartRepository = new CartRepository();
    PackRepository packRepository = new PackRepository();
    public List<CartPack> findByIdCartId (int cart_id) {
        List<CartPack> itemsByCart = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartPackRepositorySQL.FIND_BY_ID_CART_ID)) {
            statement.setInt(1, cart_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartPack cartProduct = extractFromResultSet(resultSet);
                itemsByCart.add(cartProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemsByCart;
    }

    public List<CartPack> findAll() {
        List<CartPack> cartsProducts = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartPackRepositorySQL.SELECT_ALL_CART_PACK)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CartPack cartProduct = extractFromResultSet(resultSet);
                cartsProducts.add(cartProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartsProducts;
    }

    public boolean insert(CartPack cartProduct) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartPackRepositorySQL.INSERT_CART_PACK)) {
            statement.setInt(1, cartProduct.getPack().getId());
            statement.setInt(2, cartProduct.getCart().getId());
            statement.setDouble(3, cartProduct.getAmount());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(CartPack cartProduct) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartPackRepositorySQL.UPDATE_CART_PACK)) {
            statement.setInt(1,cartProduct.getAmount());
            statement.setInt(2, cartProduct.getPack().getId());
            statement.setInt(3, cartProduct.getCart().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(CartPack cartProduct) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartPackRepositorySQL.DELETE_CART_PACK)) {
            statement.setInt(1, cartProduct.getPack().getId());
            statement.setInt(2, cartProduct.getCart().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public CartPack extractFromResultSet(ResultSet resultSet) throws SQLException {
        CartPack cartProduct = new CartPack();
        cartProduct.setPack(packRepository.findById(resultSet.getInt("pack_id")));
        cartProduct.setCart(cartRepository.findById(resultSet.getInt("cart_id")));
        cartProduct.setAmount(resultSet.getInt("amount"));
        return cartProduct;
    }

    public CartPack findByIdCartIdAndPackId(Integer pack_id, Integer cart_id) {
        CartPack cartProduct = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.CartPackRepositorySQL.FIND_BY_ID_CART_AND_PACK_ID)) {
            statement.setInt(1, pack_id);
            statement.setInt(2, cart_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                cartProduct = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartProduct;
    }
}
