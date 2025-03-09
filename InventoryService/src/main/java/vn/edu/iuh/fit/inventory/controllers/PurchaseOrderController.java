package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;
import vn.edu.iuh.fit.inventory.services.PurchaseOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    // Get all pending purchase order
    @GetMapping("/pending")
    public ResponseEntity<BaseResponse<List<PurchaseOrderResponse>>> getAllPendingPurchaseOrder() {
        List<PurchaseOrderResponse> purchaseOrders = purchaseOrderService.getAllPendingPurchaseOrder();
        return ResponseEntity.ok(
                BaseResponse
                        .<List<PurchaseOrderResponse>>builder()
                        .data(purchaseOrders)
                        .success(true)
                        .build()
        );
    }
}
