package ua.nure.pharmacy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.Customer;
import ua.nure.pharmacy.entity.Worker;
import ua.nure.pharmacy.service.impl.CustomerService;
import ua.nure.pharmacy.service.impl.WorkerService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class ProfileController {
    private final LoginController loginController;
    CustomerService customerService;
    WorkerService workerService;

    public ProfileController(LoginController loginController, CustomerService customerService,
                             WorkerService workerService) {
        this.loginController = loginController;
        this.customerService = customerService;
        this.workerService = workerService;
    }

    @GetMapping("/profile")
    public String profile(@RequestParam Integer id, Model model){
        if(LoginController.isClient) {
            Customer customer = customerService.findById(id);
            model.addAttribute("customer", customer);
            return "/client/profile";
        }else {
            Worker worker =  workerService.findById(id);
            model.addAttribute("worker", worker);
            if(LoginController.isOperator)
                return "/operator/profile_operator";
            else
                return "/storekeeper/profile_storekeeper";
        }
    }
    @GetMapping("/edit_profile")
    public String showFormEditProfile() {
        return "/client/edit_profile";
    }

    @PostMapping("/update_profile")
    public String updateProfile(@RequestParam("name") String fullName,
                                @RequestParam("login") String login,
                                @RequestParam("age") int age,
                                @RequestParam("email") String email,
                                @RequestParam("phone") String phoneNumber,
                                @RequestParam("address") String address,
                                Model model) throws UnsupportedEncodingException {

        showErrorPage(login, email, phoneNumber);

        Customer customer = customerService.findById(loginController.currentCustomer.getId());

        if (customer != null) {

            customer.setLogin(login);
            customer.setName(fullName);
            customer.setAddress(address);
            customer.setAge(age);
            customer.setEmail(email);
            customer.setPhone(phoneNumber);

            customerService.update(customer);

            model.addAttribute("user", loginController.currentCustomer);

            return profile(loginController.currentCustomer.getId(), model);
        } else {
            return "redirect:/error";
        }
    }


    public String showErrorPage(String login, String email, String phoneNumber) throws UnsupportedEncodingException {
        boolean loginFound = customerService.checkLoginExists(login);
        boolean emailFound = customerService.checkEmailExists(email);
        boolean phoneFound = customerService.checkPhoneExists(phoneNumber);
        String errorMessage = null;

        if (emailFound) {
            errorMessage = "Email already exists";
        } else if (phoneFound) {
            errorMessage = "Phone number already exists";
        } else if (loginFound) {
            errorMessage = "Login already exists";
        }

        if (errorMessage != null) {
            return "/client/error_page.html?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
        }

        return null;
    }
}
