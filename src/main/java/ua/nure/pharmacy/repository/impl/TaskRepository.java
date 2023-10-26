package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository {
    Task task;

    public List<Task> findByTask() {
        List<Task> taskList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (SQLQuery.Task.TASK)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task1 = extractFromResultSet(resultSet);
                taskList.add(task1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;
    }
    public Task extractFromResultSet(ResultSet resultSet) throws SQLException {
        Task task1 = new Task();
        task1.setName(resultSet.getString("name"));
        task1.setProducts_amount(resultSet.getInt("products_amount"));
        task1.setTotal_sum(Math.round(resultSet.getDouble("total_sum") * 100.0) / 100.0);
        return task1;
    }
}
