package vn.edu.iuh.fit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.order.model.entities.OrdersDetail;
import vn.edu.iuh.fit.order.services.OrderDetailService;

@RestController
@RequestMapping("/api/v1/orders-detail")
public class OrdersDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/home")
    public String home() {
        return "Orders Detail Service";
    }

    // Add order detail
    @PostMapping("/addOrderDetail")
    public ResponseEntity<OrdersDetail> addOrderDetail(@RequestBody OrdersDetail ordersDetail) {
        OrdersDetail createdOrdersDetail = orderDetailService.addOrderDetail(ordersDetail);
        return new ResponseEntity<>(createdOrdersDetail, HttpStatus.CREATED);
    }
}
