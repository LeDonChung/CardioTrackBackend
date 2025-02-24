package vn.edu.iuh.fit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.order.model.entities.Order;
import vn.edu.iuh.fit.order.services.OrderService;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/home")
    public String home() {
        return "Order Service";
    }
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('READ_A')")
    public String user() {
        return "Order Service USER";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "Order Service ADMIN";
    }

    @PostMapping("/home1")
    public String home1() {
        return "Order Service 1";
    }

    // Add order
    @PostMapping("/addOrder")
    public ResponseEntity<Order> addOrder(@RequestBody Order order) {
        System.out.println("----------------------------------------------------------");
        System.out.println("Order: " + order);
        System.out.println("----------------------------------------------------------");
        System.out.println("Address" + order.getAddress());
        System.out.println("----------------------------------------------------------");
        System.out.println("User" + order.getUser());
        Order createdOrder = orderService.addOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}
