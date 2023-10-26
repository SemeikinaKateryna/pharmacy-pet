package ua.nure.pharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.nure.pharmacy.entity.Order;
import ua.nure.pharmacy.entity.OrderHistory;
import ua.nure.pharmacy.service.impl.OrderHistoryService;
import ua.nure.pharmacy.service.impl.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class PharmacyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PharmacyApplication.class, args);

    }
}