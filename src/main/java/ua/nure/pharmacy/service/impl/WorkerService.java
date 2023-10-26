package ua.nure.pharmacy.service.impl;

import org.springframework.stereotype.Service;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Worker;
import ua.nure.pharmacy.repository.impl.WorkerRepository;
import ua.nure.pharmacy.service.SQLRequest;
import ua.nure.pharmacy.service.sorter.WorkerSorter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class WorkerService implements SQLRequest<Worker> {
    private final WorkerRepository workerRepository = new WorkerRepository();
    private final WorkerSorter workerSorter = new WorkerSorter();

    @Override
    public Worker findByLogin(String login) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.WorkerServiceSQL.FIND_BY_WORKER_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return workerRepository.extractFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Worker findById(int id) {
        return workerRepository.findById(id);
    }

    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    public void insert(Worker worker) {
        workerRepository.insert(worker);
    }

    public void update(Worker worker) {
        workerRepository.update(worker);
    }

    public void delete(Worker worker) {
        workerRepository.delete(worker);
    }

    public List<Worker> findAllSortedByNameAscending() {
        List<Worker> workers = workerRepository.findAll();
        return workerSorter.sortByNameAscending(workers);
    }

    public List<Worker> findAllSortedByNameDescending() {
        List<Worker> workers = workerRepository.findAll();
        return workerSorter.sortByNameDescending(workers);
    }
}