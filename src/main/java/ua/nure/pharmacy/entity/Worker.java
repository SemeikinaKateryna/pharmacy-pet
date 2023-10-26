package ua.nure.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Worker extends User {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
}