package ua.nure.pharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.*;
import ua.nure.pharmacy.service.impl.*;

import java.util.List;


@Controller
@RequestMapping("/user")
public class PersonController {
    private final WorkerService workerService;
    private final CustomerService customerService;
    private final PackService packService;

    public PersonController(WorkerService workerService, CustomerService customerService,PackService packService) {
        this.workerService = workerService;
        this.customerService = customerService;
        this.packService = packService;
    }

    @GetMapping("/workers")
    public String getAllWorkers(@RequestParam int id,Model model) {
        List<Pack> packsByProduct = packService.findByProductId(id);
        model.addAttribute("packsByProduct", packsByProduct);

        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);

        List<Worker> workers = workerService.findAll();
        model.addAttribute("workers", workers);




        //emergency
        //order_status
        return "workerList";
    }

    @GetMapping("/customers")
    public String getAllCustomers(@RequestParam int id, Model model) {
        List<Pack> packsByProduct = packService.findByProductId(id);
        model.addAttribute("packsByProduct", packsByProduct);

        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);

        List<Worker> workers = workerService.findAll();
        model.addAttribute("workers", workers);


        //emergency
        //order_status
        return "customerList";
    }
}
