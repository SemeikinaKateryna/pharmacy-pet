package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Pack;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PackRepository implements CRUDOperation<Pack>, ResultSetExtractor<Pack> {
    ProductRepository productRepository = new ProductRepository();
    DoseRepository doseRepository = new DoseRepository();
    @Override
    public Pack findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackRepositorySQL.FIND_BY_ID_PACK)) {
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
    public List<Pack> findAll() {
        List<Pack> packs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackRepositorySQL.SELECT_ALL_PACK)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Pack pack = extractFromResultSet(resultSet);
                packs.add(pack);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packs;
    }

    @Override
    public boolean insert(Pack pack) {
        if(pack.getId() != null) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement
                         (SQLQuery.PackRepositorySQL.INSERT_PACK)) {
                statement.setInt(1, pack.getId());
                statement.setInt(2, pack.getAmount());
                statement.setDouble(3, pack.getPrice());
                statement.setObject(4, pack.getProduct());
                statement.setDate(5, Date.valueOf(pack.getExpirationDate()));
                statement.setDate(6, Date.valueOf(pack.getManufactureDate()));
                statement.setObject(7, pack.getDose());
                statement.setInt(8, pack.getPacksAmount());
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return insertWithoutId(pack);
        }
    }

    private boolean insertWithoutId(Pack pack) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackRepositorySQL.INSERT_PACK_WITHOUT_ID)) {
            statement.setInt(1, pack.getAmount());
            statement.setDouble(2, pack.getPrice());
            statement.setInt(3, pack.getProduct().getId());
            statement.setDate(4, Date.valueOf(pack.getExpirationDate()));
            statement.setDate(5, Date.valueOf(pack.getManufactureDate()));
            statement.setInt(6, pack.getDose().getId());
            statement.setInt(7, pack.getPacksAmount());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Pack pack) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackRepositorySQL.UPDATE_PACK_BY_ID)) {
            statement.setInt(1, pack.getAmount());
            statement.setDouble(2, pack.getPrice());
            statement.setInt(3, pack.getProduct().getId());
            statement.setDate(4, Date.valueOf(pack.getExpirationDate()));
            statement.setDate(5, Date.valueOf(pack.getManufactureDate()));
            statement.setInt(6, pack.getDose().getId());
            statement.setInt(7, pack.getPacksAmount());

            statement.setInt(8, pack.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Pack pack) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackRepositorySQL.DELETE_PACK)) {
            statement.setInt(1, pack.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Pack extractFromResultSet(ResultSet resultSet) throws SQLException {
        Pack pack = new Pack();
        pack.setId(resultSet.getInt("id"));
        pack.setAmount(resultSet.getInt("amount"));
        pack.setPrice(resultSet.getDouble("price"));
        pack.setProduct(productRepository.findById(resultSet.getInt("product_id")));
        pack.setExpirationDate(resultSet.getDate("expiration_date").toLocalDate());
        pack.setManufactureDate(resultSet.getDate("manufacture_date").toLocalDate());
        pack.setDose(doseRepository.findById(resultSet.getInt("dose_id")));
        pack.setPacksAmount(resultSet.getInt("packs_amount"));
        return pack;
    }

    public List<Pack> findByProductId(int product_id) {
        List<Pack> packsByProductId = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.PackRepositorySQL.FIND_BY_PRODUCT_ID_PACK)) {
            statement.setInt(1, product_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Pack pack = extractFromResultSet(resultSet);
                packsByProductId.add(pack);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packsByProductId;
    }
}
