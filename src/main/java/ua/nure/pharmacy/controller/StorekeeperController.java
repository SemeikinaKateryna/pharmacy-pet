package ua.nure.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.Order;
import ua.nure.pharmacy.entity.OrderHistory;
import ua.nure.pharmacy.entity.OrderStatus;
import ua.nure.pharmacy.service.EmailService;
import ua.nure.pharmacy.service.impl.ContactService;
import ua.nure.pharmacy.service.impl.OrderHistoryService;
import ua.nure.pharmacy.service.impl.OrderService;
import ua.nure.pharmacy.service.impl.OrderStatusService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class StorekeeperController {

    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final LoginController loginController;
    private final OrderStatusService orderStatusService;
    @Autowired
    MailSender mailSender;
    private final EmailService emailService;
    private ContactService contactService;

    public StorekeeperController(OrderService orderService, OrderHistoryService orderHistoryService, LoginController loginController, OrderStatusService orderStatusService, EmailService emailService, ContactService contactService) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.loginController = loginController;
        this.orderStatusService = orderStatusService;
        this.emailService = emailService;
        this.contactService = contactService;
    }

    @GetMapping("/main_storekeeper")
    public String mainStorekeeper(Model model){
        model.addAttribute("user", loginController.storekeeper);
        return "/storekeeper/main_storekeeper";
   }

    @GetMapping("/approved_orders_storekeeper")
    public String approvedOrders(Model model){
        List<Order> approvedOrders = orderService.findByStatusIdAndOrderByEmergency(2);

        model.addAttribute("orders", approvedOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : approvedOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.storekeeper);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/storekeeper/orders_storekeeper";
    }

    @GetMapping("/processing_orders")
    public String processingOrders(Model model){
        List<Order> processingOrders = orderService.findByStatusId(4);
        model.addAttribute("orders", processingOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : processingOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.storekeeper);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/storekeeper/orders_storekeeper";
    }

    @GetMapping("/unable_orders")
    public String unableOrders(Model model){
        List<Order> unableOrders = orderService.findByStatusIdAndOrderByEmergency(5);
        model.addAttribute("orders", unableOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : unableOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.storekeeper);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/storekeeper/orders_storekeeper";
    }

    @GetMapping("/collected_orders")
    public String collectedOrders(Model model){
        List<Order> collectedOrders = orderService.findByStatusIdAndOrderByEmergency(6);
        model.addAttribute("orders", collectedOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : collectedOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.storekeeper);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/storekeeper/orders_storekeeper";
    }
    @GetMapping("/taken_orders")
    public String takenOrders(Model model){
        List<Order> takenOrders = orderService.findByStatusIdAndOrderByEmergency(7);
        model.addAttribute("orders", takenOrders);

        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : takenOrders) {
            orderHistoriesMap.put(order,
                    orderHistoryService.findByOrderId(order.getId()));
        }

        model.addAttribute("user", loginController.storekeeper);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/storekeeper/orders_storekeeper";
    }

    //передается айди заказа
    @PostMapping("/operate_order_storekeeper")
    public String operateOrderStorekeeper(@RequestParam(required = false) Integer order_id,
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
        orderHistory.setWorker(loginController.storekeeper);
        orderHistoryService.insert(orderHistory);

        String message = "Замовлення № " + byId.getId() + " " + byId.getStatus().getName().toLowerCase()
                + " комірником.";
        emailService.sendEmail(byId.getCustomer().getEmail(),
                "Статус замовлення змінено", message);

        model.addAttribute("user", loginController.operator);
        return processingOrders(model);
    }
}
