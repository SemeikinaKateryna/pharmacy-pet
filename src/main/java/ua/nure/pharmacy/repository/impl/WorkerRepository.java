package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Worker;
import ua.nure.pharmacy.repository.CRUDOperation;
import ua.nure.pharmacy.repository.ResultSetExtractor;

import java.util.List;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class WorkerRepository implements CRUDOperation<Worker>, ResultSetExtractor<Worker> {
    RoleRepository roleRepository = new RoleRepository();

    @Override
    public Worker findById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.WorkerRepositorySQL.FIND_BY_ID_WORKER)) {
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
    public List<Worker> findAll() {
        List<Worker> workers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.WorkerRepositorySQL.SELECT_ALL_WORKER)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Worker worker = extractFromResultSet(resultSet);
                workers.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workers;
    }

    @Override
    public boolean insert(Worker worker) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.WorkerRepositorySQL.INSERT_WORKER)) {
            statement.setInt(1, worker.getId());
            statement.setDate(2, Date.valueOf(worker.getStartDate()));
            if (worker.getEndDate() != null) {
                statement.setDate(3, Date.valueOf(worker.getEndDate()));
            } else {
                statement.setNull(3, Types.DATE);
            }
            statement.setString(4, worker.getPosition());
            statement.setString(5, worker.getLogin());
            statement.setString(6, worker.getPassword());
            statement.setInt(7, worker.getRole().getId());
            statement.setString(8, worker.getName());
            statement.setString(9, worker.getAddress());
            statement.setDate(10, Date.valueOf(worker.getRegisterDate()));
            statement.setString(11, worker.getPhone());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Worker worker) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.WorkerRepositorySQL.UPDATE_WORKER_BY_ID)) {
            statement.setDate(1, Date.valueOf(worker.getStartDate()));
            if (worker.getEndDate() != null) {
                statement.setDate(2, Date.valueOf(worker.getEndDate()));
            } else {
                statement.setNull(2, Types.DATE);
            }
            statement.setString(3, worker.getPosition());
            statement.setString(4, worker.getLogin());
            statement.setString(5, worker.getPassword());
            statement.setInt(6, worker.getRole().getId());
            statement.setString(7, worker.getName());
            statement.setString(8, worker.getAddress());
            statement.setDate(9, Date.valueOf(worker.getRegisterDate()));
            statement.setInt(10, worker.getId());
            statement.setString(11, worker.getPhone());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Worker worker) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.WorkerRepositorySQL.DELETE_WORKER)) {
            statement.setInt(1, worker.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Worker extractFromResultSet(ResultSet resultSet) throws SQLException {
        Worker worker = new Worker();
        worker.setId(resultSet.getInt("id"));
        worker.setStartDate(resultSet.getDate("start_date").toLocalDate());

        Date endDate = resultSet.getDate("end_date");
        if (endDate != null) {
            worker.setEndDate(endDate.toLocalDate());
        } else {
            worker.setEndDate(null);
        }

        worker.setPosition(resultSet.getString("position"));
        worker.setLogin(resultSet.getString("login"));
        worker.setPassword(resultSet.getString("password"));
        worker.setRole(roleRepository.findById(resultSet.getInt("role_id")));
        worker.setName(resultSet.getString("name"));
        worker.setAddress(resultSet.getString("address"));
        worker.setRegisterDate(resultSet.getDate("register_date").toLocalDate());
        worker.setPhone(resultSet.getString("phone"));
        return worker;
    }
}