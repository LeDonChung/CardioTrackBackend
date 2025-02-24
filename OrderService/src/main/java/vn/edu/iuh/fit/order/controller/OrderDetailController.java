package vn.edu.iuh.fit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.order.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.order.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.order.model.dto.response.OrderDetailResponse;
import vn.edu.iuh.fit.order.services.OrderDetailService;

@RestController
@RequestMapping("/api/v1/order-detail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<BaseResponse<OrderDetailResponse>> addOrderDetail(@RequestBody OrderDetailRequest request){
        OrderDetailResponse orderDetail = orderDetailService.save(request);
        return ResponseEntity.ok(
                BaseResponse
                        .<OrderDetailResponse>builder()
                        .data(orderDetail)
                        .success(true)
                        .build()
        );
    }
}
