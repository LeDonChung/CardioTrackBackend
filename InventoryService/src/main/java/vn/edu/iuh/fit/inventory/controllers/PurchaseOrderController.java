package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderDetailResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;
import vn.edu.iuh.fit.inventory.services.PurchaseOrderDetailService;
import vn.edu.iuh.fit.inventory.services.PurchaseOrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderDetailService purchaseOrderDetailService;

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

    // Lấy chi tiết phiếu mua hàng theo id phiếu mua hàng
    @GetMapping("/{id}/detail")
    public ResponseEntity<BaseResponse<List<PurchaseOrderDetailResponse>>> getPurchaseOrderDetailByPurchaseOrderId(@PathVariable Long id) {
        List<PurchaseOrderDetailResponse> purchaseOrderDetailResponses = purchaseOrderDetailService.getAllPurchaseOrderDetailByPurchaseOrderId(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<PurchaseOrderDetailResponse>>builder()
                        .data(purchaseOrderDetailResponses)
                        .success(true)
                        .build()
        );
    }
}
