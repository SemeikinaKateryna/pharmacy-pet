package ua.nure.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.nure.pharmacy.entity.Cart;
import ua.nure.pharmacy.entity.Customer;
import ua.nure.pharmacy.entity.User;
import ua.nure.pharmacy.entity.Worker;
import ua.nure.pharmacy.service.EmailService;
import ua.nure.pharmacy.service.impl.CartService;
import ua.nure.pharmacy.service.impl.CustomerService;
import ua.nure.pharmacy.service.impl.UserService;

@Controller
public class LoginController {
    private final UserService userService;
    private final CustomerService customerService;
    private final EmailService emailService;
    @Autowired
    MailSender mailSender;
    public static boolean isClient = false;
    public static boolean isOperator = false;
    public static boolean isStorekeeper = false;
    public Customer currentCustomer = new Customer();
    private final PasswordEncoder passwordEncoder;
    private CartService cartService;
    public Worker operator = new Worker();
    public Worker storekeeper = new Worker();

    public LoginController(UserService userService, CustomerService customerService,
                           EmailService emailService, PasswordEncoder passwordEncoder,
                           CartService cartService) {
        this.userService = userService;
        this.customerService = customerService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "/guest/login";
    }

    @PostMapping("/submit_login")
    public String submitLoginForm(@RequestParam("username") String username,
                                  @RequestParam("password") String password,
                                  RedirectAttributes attributes, Model model) {
        User user = userService.checkUserCredentials(username, password);
        if (user != null) {
            switch (user.getRole().getName()) {
                case "Клієнт" -> {
                    isClient = true;
                    currentCustomer = (Customer) user;
                    CartController.cartCurrent = new Cart(currentCustomer);
                    cartService.insert(CartController.cartCurrent);
                    model.addAttribute("user", user);
                    return "/client/main_client";
                }
                case "Оператор" -> {
                    isOperator = true;
                    operator = (Worker) user;
                    model.addAttribute("user", user);
                    return "/operator/main_operator";
                }
                case "Комірник" -> {
                    isStorekeeper = true;
                    storekeeper = (Worker) user;
                    model.addAttribute("user", user);
                    return "/storekeeper/main_storekeeper";
                }
                default -> {
                    return "index";
                }
            }
        } else {
            attributes.addAttribute("error", true);
            return "redirect:/login";
        }
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "/guest/registration";
    }


    @PostMapping("/submit_registration")
    public String submitRegistrationForm(@RequestParam("Full Name") String fullName,
                                         @RequestParam("Login") String login,
                                         @RequestParam("Email") String email,
                                         @RequestParam("Phone Number") String phoneNumber,
                                         @RequestParam("Age") int age,
                                         @RequestParam("Address") String address,
                                         @RequestParam("Password") String password,
                                         RedirectAttributes redirectAttributes,
                                         Model model){

        Customer customer = new Customer();

        customer.setLogin(login);
        customer.setPassword(passwordEncoder.encode(password));
        customer.setName(fullName);
        customer.setPhone(phoneNumber);
        customer.setAge(age);
        customer.setEmail(email);
        customer.setAddress(address);

        userService.insert(customer);
        customerService.insert(customer);

        Customer customerFromDb = customerService.findByLogin(login);
        if (customerFromDb != null) {
            //сохранение прошло успешно
            if (!StringUtils.isEmpty(customer.getEmail())) {
                String message = "Ваш логін для входу: "
                        + customerFromDb.getLogin()
                        + ".\nПриємних вам покупок!";
                emailService.sendEmail(customerFromDb.getEmail(),
                        "Ви успішно зареєструвались на сайті онлайн-аптеки",
                        message);
            }
        }
        CartController.cartCurrent = new Cart(customerFromDb);
        cartService.insert(CartController.cartCurrent);
        return "redirect:/login";
    }

    @GetMapping("/main_client")
    public String mainClient(Model model) {
        model.addAttribute("user", currentCustomer);
        return "/client/main_client";
    }

    @GetMapping("/logout")
    public String logout() {
        if (LoginController.isClient)
            isClient = false;
        if (LoginController.isOperator)
            isOperator = false;
        if (LoginController.isStorekeeper)
            isStorekeeper = false;
        return "index";
    }
}
