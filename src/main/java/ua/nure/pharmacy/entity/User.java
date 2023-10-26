package ua.nure.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public abstract class User {
    private Integer id;
    private String login;
    private String password;
    private Role role;
    private String name;
    private String address;
    private LocalDate registerDate;
    private String phone;
}