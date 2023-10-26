package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Manufacturer;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManufacturerRepository implements CRUDOperation<Manufacturer>,
        ResultSetExtractor<Manufacturer> {


    @Override
    public Manufacturer findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ManufacturerRepositorySQL.FIND_BY_ID_MANUFACTURER)) {
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
    public List<Manufacturer> findAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ManufacturerRepositorySQL.SELECT_ALL_MANUFACTURER)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Manufacturer manufacturer = extractFromResultSet(resultSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    @Override
    public boolean insert(Manufacturer manufacturer) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.ManufacturerRepositorySQL.INSERT_MANUFACTURER)) {
            statement.setInt(1, manufacturer.getId());
            statement.setString(2, manufacturer.getName());
            statement.setString(3, manufacturer.getCountry());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Manufacturer manufacturer) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ManufacturerRepositorySQL.UPDATE_MANUFACTURER_BY_ID)) {
            statement.setInt(1,manufacturer.getId());
            statement.setString(2, manufacturer.getName());
            statement.setString(3, manufacturer.getCountry());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ManufacturerRepositorySQL.DELETE_MANUFACTURER)) {
            statement.setInt(1, manufacturer.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Manufacturer extractFromResultSet(ResultSet resultSet) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(resultSet.getInt("id"));
        manufacturer.setName(resultSet.getString("name"));
        manufacturer.setCountry(resultSet.getString("country"));
        return manufacturer;
    }

    public Manufacturer findByName(String param) {
        Manufacturer manufacturer = null;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.ManufacturerRepositorySQL.FIND_BY_NAME_MANUFACTURER)) {
            statement.setString(1, param);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                manufacturer = extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manufacturer;

    }
}
