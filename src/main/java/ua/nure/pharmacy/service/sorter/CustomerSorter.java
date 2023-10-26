package ua.nure.pharmacy.service.sorter;

import org.springframework.stereotype.Component;
import ua.nure.pharmacy.entity.Customer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class CustomerSorter {
    public List<Customer> sortByNameAscending(List<Customer> customers) {
        List<Customer> sortedCustomers = new ArrayList<>(customers);
        sortedCustomers.sort(Comparator.comparing(Customer::getName));
        return sortedCustomers;
    }

    public List<Customer> sortByNameDescending(List<Customer> customers) {
        List<Customer> sortedCustomers = new ArrayList<>(customers);
        sortedCustomers.sort(Comparator.comparing(Customer::getName).reversed());
        return sortedCustomers;
    }
}
