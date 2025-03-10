package vn.edu.iuh.fit.order.controller;

import jakarta.ws.rs.PUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.exceptions.OrderException;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse2;
import vn.edu.iuh.fit.order.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderResponse>> addOrder(@RequestBody OrderRequest request) throws OrderException {
        OrderResponse order = orderService.save(request);
        return ResponseEntity.ok(
                BaseResponse
                        .<OrderResponse>builder()
                        .data(order)
                        .success(true)
                        .build()
        );
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<BaseResponse<OrderResponse>> changeStatus(@PathVariable Long id, @RequestParam OrderStatus status) throws OrderException {
        OrderResponse order = orderService.changeStatus(id, status);
        return ResponseEntity.ok(
                BaseResponse
                        .<OrderResponse>builder()
                        .data(order)
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getOrdersByUserId(
            @RequestHeader("Authorization") String authorizationHeader, // lấy token từ header
            @PathVariable Long id) throws OrderException {
        String token = authorizationHeader.substring(7);
        // Nếu token hợp lệ, lấy danh sách đơn hàng của người dùng
        List<OrderResponse> orderResponses = orderService.getOrdersByUserId(id);

        return ResponseEntity.ok(
                BaseResponse
                        .<List<OrderResponse>>builder()
                        .data(orderResponses)
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/recommend")
    public ResponseEntity<BaseResponse<List<OrderResponse>>> recommend() throws OrderException {
        List<OrderResponse> order = orderService.recommend();
        return ResponseEntity.ok(
                BaseResponse
                        .<List<OrderResponse>>builder()
                        .data(order)
                        .success(true)
                        .build()
        );
    }

}
