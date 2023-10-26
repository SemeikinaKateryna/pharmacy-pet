package ua.nure.pharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.pharmacy.entity.Customer;
import ua.nure.pharmacy.entity.Order;
import ua.nure.pharmacy.service.impl.CustomerService;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "customerDetail";
        } else {
            return "customerNotFound";
        }
    }

    @GetMapping
    public String getAllCustomer(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customerList";
    }

    @PostMapping
    public String addCustomer(@ModelAttribute Customer customer) {
        customerService.insert(customer);
        return "redirect:/customer";
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable int id, @ModelAttribute Customer customer) {
        Customer existingCustomer = customerService.findById(id);
        if (existingCustomer != null) {
            customer.setId(id);
            customerService.update(customer);
            return "redirect:/customer";
        } else {
            return "customerNotFound";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            customerService.delete(customer);
            return "redirect:/customer";
        } else {
            return "customerNotFound";
        }
    }
}


