package ua.nure.pharmacy.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.pharmacy.entity.Customer;
import ua.nure.pharmacy.entity.User;
import ua.nure.pharmacy.repository.impl.CustomerRepository;
import ua.nure.pharmacy.repository.impl.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User checkUserCredentials(String username, String password) {
        List<User> users = userRepository.getAllUsers();
        User userFound = null;
        for (User user : users) {
            if (user.getLogin().equals(username)) {
                if (user.getPassword().equals(password)) {
                    userFound = user;
                    break;
                } else if (passwordEncoder.matches(password, user.getPassword())) {
                    userFound = user;
                    break;
                }
            }
        }
        return userFound;
    }

    public boolean insert(Customer customer) {
        return userRepository.insert(customer);
    }

    public boolean delete(Customer customer) {
        return userRepository.delete(customer);
    }

}

