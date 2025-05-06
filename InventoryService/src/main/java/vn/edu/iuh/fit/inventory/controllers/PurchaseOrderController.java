package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.enums.PurchaseOrderStatus;
import vn.edu.iuh.fit.inventory.exceptions.PurchaseOrderException;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderRequest;
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

    //verify
    @GetMapping("/permission")
    @PreAuthorize("hasAuthority('PURCHASE_ORDER')")
    public ResponseEntity<String> purchaseOrder() {
        return ResponseEntity.ok("You have permission to access purchase order");
    }

    // Lấy tất cả phiếu mua hàng
    @GetMapping
    public ResponseEntity<BaseResponse<PageDTO<PurchaseOrderResponse>>> getAllPurchaseOrder(@RequestParam(defaultValue = "0") int page,
                                                                                            @RequestParam(defaultValue = "10") int size,
                                                                                            @RequestParam(required = false) String sortBy,
                                                                                            @RequestParam(required = false) String sortName) {
        PageDTO<PurchaseOrderResponse> purchaseOrders = purchaseOrderService.getAllPurchaseOrder(page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<PurchaseOrderResponse>>builder()
                        .data(purchaseOrders)
                        .success(true)
                        .build()
        );
    }

    // Tạo mới phiếu mua hàng
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PURCHASE_ORDER')")
    public ResponseEntity<BaseResponse<PurchaseOrderResponse>> save(@RequestBody PurchaseOrderRequest request) throws PurchaseOrderException {
        PurchaseOrderResponse response = purchaseOrderService.save(request);
        return ResponseEntity.ok(
                BaseResponse
                        .<PurchaseOrderResponse>builder()
                        .data(response)
                        .success(true)
                        .build()
        );
    }

    // Get all pending purchase order
    @GetMapping("/pending")
    public ResponseEntity<BaseResponse<PageDTO<PurchaseOrderResponse>>> getAllPendingPurchaseOrder(@RequestParam(defaultValue = "0") int page,
                                                                                                   @RequestParam(defaultValue = "10") int size,
                                                                                                   @RequestParam(required = false) String sortBy,
                                                                                                   @RequestParam(required = false) String sortName) {
        PageDTO<PurchaseOrderResponse> purchaseOrders = purchaseOrderService.getAllPendingPurchaseOrder(page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<PurchaseOrderResponse>>builder()
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

    // Thay đổi trạng thái của đơn mua hàng
    @PutMapping("/change-status/{id}")
    public ResponseEntity<BaseResponse<PurchaseOrderResponse>> changeStatus(@PathVariable Long id, @RequestParam PurchaseOrderStatus status) throws PurchaseOrderException {
        PurchaseOrderResponse purchaseOrderResponse = purchaseOrderService.changeStatus(id, status);
        return ResponseEntity.ok(
                BaseResponse
                        .<PurchaseOrderResponse>builder()
                        .data(purchaseOrderResponse)
                        .success(true)
                        .build()
        );
    }

    // Thêm đánh giá cho chi tiết phiếu mua hàng
    @PutMapping("/{id}/review")
    public ResponseEntity<BaseResponse<String>> addReview(@PathVariable Long id, @RequestBody PurchaseOrderDetailRequest request) {
        try {
            // Xử lý đánh giá cho thuốc hoặc danh mục
            if (request.getCategory() != null && request.getMedicine() == null) {
                purchaseOrderDetailService.addReviewByCategory(id, request);
            } else if (request.getMedicine() != null) {
                purchaseOrderDetailService.addReviewByMedicine(id, request);
            } else {
                return ResponseEntity.badRequest().body(
                        BaseResponse.<String>builder().data("Danh mục hoặc thuốc phải được chọn.").success(false).build()
                );
            }

            return ResponseEntity.ok(
                    BaseResponse.<String>builder().data("Đánh giá đã được cập nhật thành công").success(true).build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    BaseResponse.<String>builder().data("Có lỗi xảy ra: " + e.getMessage()).success(false).build()
            );
        }
    }
}
