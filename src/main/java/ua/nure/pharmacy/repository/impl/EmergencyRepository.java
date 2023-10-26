package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Emergency;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



@Repository
public class EmergencyRepository implements CRUDOperation<Emergency>, ResultSetExtractor<Emergency> {
    @Override
    public Emergency findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.EmergencyRepositorySQL.FIND_BY_ID_EMERGENCY)) {
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
    public List<Emergency> findAll() {
        List<Emergency> emergencies = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.EmergencyRepositorySQL.SELECT_ALL_EMERGENCY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Emergency emergency = extractFromResultSet(resultSet);
                emergencies.add(emergency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emergencies;
    }

    @Override
    public boolean insert(Emergency emergency) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.EmergencyRepositorySQL.INSERT_EMERGENCY)) {
            statement.setInt(1, emergency.getId());
            statement.setString(2, emergency.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Emergency emergency) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.EmergencyRepositorySQL.UPDATE_EMERGENCY_BY_ID)) {
            statement.setInt(1,emergency.getId());
            statement.setString(2, emergency.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Emergency emergency) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.EmergencyRepositorySQL.DELETE_EMERGENCY)) {
            statement.setInt(1, emergency.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Emergency extractFromResultSet(ResultSet resultSet) throws SQLException {
        Emergency emergency = new Emergency();
        emergency.setId(resultSet.getInt("id"));
        emergency.setName(resultSet.getString("name"));
        return emergency;
    }
}
