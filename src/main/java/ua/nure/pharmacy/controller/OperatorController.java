package ua.nure.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.*;
import ua.nure.pharmacy.service.EmailService;
import ua.nure.pharmacy.service.impl.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OperatorController {
    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final LoginController loginController;
    private final OrderStatusService orderStatusService;
    @Autowired
    MailSender mailSender;
    private final EmailService emailService;
    private ContactService contactService;

    private ProductService productService;
    private PackService packService;
    private ManufacturerService manufacturerService;
    private DoseService doseService;
    public OperatorController(OrderService orderService, OrderHistoryService orderHistoryService,
                              LoginController loginController, OrderStatusService orderStatusService,
                              EmailService emailService, ContactService contactService,
                              ProductService productService, PackService packService,
                              ManufacturerService manufacturerService, DoseService doseService) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.loginController = loginController;
        this.orderStatusService = orderStatusService;
        this.emailService = emailService;
        this.contactService = contactService;
        this.productService = productService;
        this.packService = packService;
        this.manufacturerService = manufacturerService;
        this.doseService = doseService;
    }

    @GetMapping("/main_operator")
    public String mainOperator(Model model){
        model.addAttribute("user", loginController.operator);
        return "/operator/main_operator";
    }

    @GetMapping("/all_orders")
    public String allOrders(Model model){
        List<Order> all = orderService.findAll();
        model.addAttribute("orders", all);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : all) {
            orderHistoriesMap.put(order,orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.operator);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/operator/orders";
    }

    @GetMapping("/new_orders")
    public String newOrders(Model model){
        List<Order> newOrders = orderService.findByStatusId(1);
        model.addAttribute("orders", newOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : newOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.operator);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/operator/orders";
    }

    @GetMapping("/approved_orders")
    public String approvedOrders(Model model){
        List<Order> approvedOrders = orderService.findByStatusId(2);
        model.addAttribute("orders", approvedOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : approvedOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.operator);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/operator/orders";
    }

    @GetMapping("/rejected_orders")
    public String rejectedOrders(Model model){
        List<Order> rejectedOrders = orderService.findByStatusId(3);
        model.addAttribute("orders", rejectedOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : rejectedOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.operator);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/operator/orders";
    }

    //передается айди заказа
    @PostMapping("/operate_order")
    public String operateOrder(@RequestParam(required = false) Integer order_id,
                               @RequestParam(required = false) Integer status,
                               Model model){
        Order byId = orderService.findById(order_id);
        OrderStatus orderStatus = orderStatusService.findById(status);
        byId.setStatus(orderStatus);
        orderService.update(byId);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder(byId);
        orderHistory.setDate(LocalDate.now());
        orderHistory.setStatus(orderStatus);
        orderHistory.setWorker(loginController.operator);
        orderHistoryService.insert(orderHistory);

        String message = "Замовлення № " + byId.getId() + " " + byId.getStatus().getName().toLowerCase()
                + " оператором.";
            emailService.sendEmail(byId.getCustomer().getEmail(),
                    "Статус замовлення змінено", message);

        model.addAttribute("user", loginController.operator);
        return allOrders(model);
    }

    @GetMapping("/manage_products")
    public String manageProducts(Model model){
        List<Pack> packs = packService.findAll();
        model.addAttribute("packs", packs);
        model.addAttribute("user", loginController.operator);
        return "/operator/manage_products";
    }

    @GetMapping("/add_medicine")
    public String addMedicinePage(Model model){
        List<Manufacturer> manufacturers = manufacturerService.findAll();
        List<Pack> packs = packService.findAll();
        model.addAttribute("user", loginController.operator);
        model.addAttribute("packs", packs);
        model.addAttribute("manufacturers", manufacturers);
        return "/operator/add_product";
    }

    @PostMapping("/add_product_finish")
    public String updateEmployeeFinish(@RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam int amount,
                                       @RequestParam LocalDate manufacture_date,
                                       @RequestParam LocalDate expiration_date,
                                       @RequestParam int id, //manufacturer_id
                                       @RequestParam int dose_id,
                                       @RequestParam Double price,
                                       @RequestParam Integer packs_amount,
                                       Model model) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setManufacturer(manufacturerService.findById(id));
        product.setPhotoUrl(null);
        productService.insert(product);

        Product productFromDb = productService.findLast();

        Pack pack = new Pack();
        pack.setAmount(amount);
        pack.setManufactureDate(manufacture_date);
        pack.setExpirationDate(expiration_date);
        pack.setDose(doseService.findById(dose_id));
        pack.setPrice(price);
        pack.setPacksAmount(packs_amount);
        pack.setProduct(productFromDb);
        packService.insert(pack);

        List<Product> products = productService.findAll();
        model.addAttribute("products", products);

        Map<Product, List<Pack>> productsPacks = new HashMap<>();
        for (Product product3 :  products) {
            List<Pack> productPacks
                    = packService.findByProductId(product3.getId());
            productsPacks.put(product3, productPacks);
        }
        model.addAttribute("productsPacks", productsPacks);
        model.addAttribute("user", loginController.operator);
        return "/operator/products_operator";
    }

}
