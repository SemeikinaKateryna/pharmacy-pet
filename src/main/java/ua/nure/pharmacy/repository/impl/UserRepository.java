package ua.nure.pharmacy.repository.impl;

import org.springframework.stereotype.Repository;
import ua.nure.pharmacy.database.DatabaseConnection;
import ua.nure.pharmacy.database.SQLQuery;
import ua.nure.pharmacy.entity.Customer;
import ua.nure.pharmacy.entity.User;
import ua.nure.pharmacy.entity.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
   private final CustomerRepository customerRepository;
   private final WorkerRepository workerRepository;

   public UserRepository() {
      this.customerRepository = new CustomerRepository();
      this.workerRepository = new WorkerRepository();
   }

   public List<User> getAllUsers() {

      List<Customer> customers = customerRepository.findAll();
      List<User> users = new ArrayList<>(customers);

      List<Worker> workers = workerRepository.findAll();
      users.addAll(workers);

      return users;
   }
   public boolean insert(User user){
      try (Connection connection = DatabaseConnection.getConnection();
           PreparedStatement statement = connection.prepareStatement
                   (SQLQuery.UserRepository.INSERT_USER)) {
         statement.setString(1, user.getLogin());
         statement.setString(2, user.getPassword());
         statement.setString(3, user.getName());
         statement.setString(4, user.getAddress());
         statement.setString(5,user.getPhone());
         statement.executeUpdate();
         return true;
      }
         catch (SQLException e) {
            e.printStackTrace();
            return false;
         }

   }

   public boolean delete(Customer customer) {
      try (Connection connection = DatabaseConnection.getConnection();
           PreparedStatement statement = connection.prepareStatement
                   (SQLQuery.UserRepository.DELETE_USER)) {
         statement.setInt(1, customer.getId());
         statement.executeUpdate();
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }
}
