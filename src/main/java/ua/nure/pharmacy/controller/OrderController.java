package ua.nure.pharmacy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.pharmacy.entity.*;
import ua.nure.pharmacy.repository.impl.OrderHistoryRepository;
import ua.nure.pharmacy.service.EmailService;
import ua.nure.pharmacy.service.impl.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    private final ContactService contactService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private LoginController loginController;
    private OrderStatusService orderStatusService;
    private CartPackService cartPackService;
    private EmergencyService emergencyService;
    private PackOrderService packOrderService;
    private CartController cartController;
    @Autowired
    MailSender mailSender;
    private final EmailService emailService;
    private OrderHistoryService orderHistoryService;

    public OrderController(ContactService contactService, OrderService orderService,
                           CustomerService customerService, LoginController loginController,
                           OrderStatusService orderStatusService, CartPackService cartPackService,
                           EmergencyService emergencyService, PackOrderService packOrderService,
                           CartController cartController, EmailService emailService,
                           OrderHistoryService orderHistoryService) {
        this.contactService = contactService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.loginController = loginController;
        this.orderStatusService = orderStatusService;
        this.cartPackService = cartPackService;
        this.emergencyService = emergencyService;
        this.packOrderService = packOrderService;
        this.cartController = cartController;
        this.emailService = emailService;
        this.orderHistoryService = orderHistoryService;
    }

    @GetMapping("/order_extra_contact")
    public String showExtraContactPage() {
        return "/client/order_extra_contact";
    }


    @PostMapping("/submit_extra_contact")
    public String submitOrderExtraContact(@RequestParam String fullName,
                                          @RequestParam String email,
                                          @RequestParam String phone) {
        Order orderFromDB = orderService.findLast();

        Contact contactExtra = new Contact();
        contactExtra.setOrder(orderFromDB);
        contactExtra.setName(fullName);
        contactExtra.setEmail(email);
        contactExtra.setPhone(phone);
        contactService.insert(contactExtra);

        return "/client/emergency";
    }

    @GetMapping("/order_contact")
    public String showOrderPage(Model model) {
        Order order = new Order();
        Customer byId = customerService.findById(loginController.currentCustomer.getId());

        order.setCustomer(byId);
        order.setStatus(orderStatusService.findById(1));
        order.setPrice(CartController.cartCurrent.getPrice());
        order.setEmergency(emergencyService.findById(2));
        orderService.insert(order);

        Order orderFromDB = orderService.findLast();

        Contact contactDefault = new Contact();
        contactDefault.setOrder(orderFromDB);
        contactDefault.setName(byId.getName());
        contactDefault.setEmail(byId.getEmail());
        contactDefault.setPhone(byId.getPhone());
        contactService.insert(contactDefault);

        model.addAttribute("contactDef", contactDefault);

        return "/client/order_contact";
    }


    @PostMapping("/submit_order")
    public String submitOrder(@RequestParam Integer urgency, Model model) {
        if (urgency == 1) {
            Order orderFromDB = orderService.findLast();
            orderFromDB.setEmergency(emergencyService.findById(urgency));
            orderService.update(orderFromDB);
        }

        Order orderFromDB = orderService.findLast();

        List<CartPack> cartPackList = cartPackService.findByIdCartId
                (CartController.cartCurrent.getId());

        for (CartPack cartItem : cartPackList) {
            PackOrder packOrder = new PackOrder();
            packOrder.setOrder(orderFromDB);
            packOrder.setPack(cartItem.getPack());
            packOrder.setPrice(cartItem.getPack().getPrice());
            packOrder.setAmount(cartItem.getAmount());
            packOrderService.insert(packOrder);
        }

        List<PackOrder> packsInOrder = packOrderService.findByOrderId(orderFromDB.getId());

        List<Contact> contacts = contactService.findByIdOrderId(orderFromDB.getId());

        Emergency emergency = emergencyService.findById(orderFromDB.getEmergency().getId());
        model.addAttribute("user", loginController.currentCustomer);
        model.addAttribute("order", orderFromDB);
        model.addAttribute("packsOrder", packsInOrder);
        model.addAttribute("contacts",contacts);
        model.addAttribute("emergency", emergency.getName());
        return "/client/final_submit_order";
    }


    @GetMapping("/cancel")
    public String cancelOrder(Model model){
        Order orderFromDB = orderService.findLast();

        List<PackOrder> list = packOrderService.findByOrderId(orderFromDB.getId());

        for (PackOrder orderItem : list) {
            packOrderService.delete(orderItem);
        }

        List<Contact> contacts = contactService.findByIdOrderId(orderFromDB.getId());
        for ( Contact contact: contacts) {
            contactService.delete(contact);
        }

        orderService.delete(orderFromDB);

        model.addAttribute("user", loginController.currentCustomer);

        List<CartPack> cartPackList = cartPackService.findByIdCartId
                (CartController.cartCurrent.getId());
        model.addAttribute("cartCurrentProduct", cartPackList);
        model.addAttribute("cartCurrent",CartController.cartCurrent);
        return "/client/cart";
    }

    @GetMapping("/submit_contact")
    public String submitContact() {
        return "/client/emergency";
    }

    @GetMapping("/thanks")
    public String thanks(){
        Order orderFromDB = orderService.findLast();

        List<Contact> contacts = contactService.findByIdOrderId(orderFromDB.getId());

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder(orderFromDB);
        orderHistory.setStatus(orderStatusService.findById(1));
        orderHistory.setDate(LocalDate.now());
        orderHistory.setWorker(null);
        orderHistoryService.insert(orderHistory);

        for(Contact contact : contacts) {
            String message = "Код вашого замовлення: " + orderFromDB.getId()
                    + ".\nБільш детальну інформацію можете переглянути на вкладці Мої замовлення," +
                    " перейшовши за номером замовлення.";
            emailService.sendEmail(contact.getEmail(),
                    "Дякуємо за ваше замовлення!",
                    message);
        }

        CartController.cartCurrent.setPrice(0.0);
        cartController.cartService.update(CartController.cartCurrent);

        List<CartPack> cartPackList = cartPackService.findByIdCartId(CartController.cartCurrent.getId());
        for(CartPack cartPack : cartPackList){
            cartPackService.delete(cartPack);
        }


        return "/client/thanks";
    }
    @GetMapping("/orders")
    public String orders(@RequestParam int id, Model model){
        List<Order> orders = orderService.findByIdCustomerId(id);
        Map<Order,List<OrderHistory>> orderHistoriesMap = new HashMap<>();
        for (Order order : orders) {
            orderHistoriesMap.put(order,orderHistoryService.findByOrderId(order.getId()));
        }
        model.addAttribute("orders", orders);
        model.addAttribute("user", loginController.currentCustomer);
        model.addAttribute("orderHistoriesMap", orderHistoriesMap);
        return "/client/my_orders";
    }

    @GetMapping("/order")
    public String order(@RequestParam int id, Model model){
        Order order = orderService.findById(id);
        List<Contact> contacts = contactService.findByIdOrderId(order.getId());
        List<PackOrder> packOrders = packOrderService.findByOrderId(order.getId());

        model.addAttribute("order", order);
        model.addAttribute("contacts", contacts);
        model.addAttribute("packsOrder", packOrders);
        model.addAttribute("user", loginController.currentCustomer);
        return "/client/order";
    }
}