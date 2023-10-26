package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Dose;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoseRepository implements CRUDOperation<Dose>, ResultSetExtractor<Dose> {
    @Override
    public Dose findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.DoseRepositorySQL.FIND_BY_ID_DOSE)) {
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
    public List<Dose> findAll() {
        List<Dose> doses = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.DoseRepositorySQL.SELECT_ALL_DOSE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dose dose = extractFromResultSet(resultSet);
                doses.add(dose);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doses;
    }

    @Override
    public boolean insert(Dose dose) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.DoseRepositorySQL.INSERT_DOSE)) {
            statement.setInt(1, dose.getId());
            statement.setDouble(2, dose.getAmount());
            statement.setString(3, dose.getMeasure());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Dose dose) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.DoseRepositorySQL.UPDATE_DOSE_BY_ID)) {
            statement.setInt(1,dose.getId());
            statement.setDouble(2, dose.getAmount());
            statement.setString(3, dose.getMeasure());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Dose dose) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.DoseRepositorySQL.DELETE_DOSE)) {
            statement.setInt(1, dose.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Dose extractFromResultSet(ResultSet resultSet) throws SQLException {
        Dose dose = new Dose();
        dose.setId(resultSet.getInt("id"));
        dose.setAmount(resultSet.getDouble("amount"));
        dose.setMeasure(resultSet.getString("measure"));
        return dose;
    }
}
