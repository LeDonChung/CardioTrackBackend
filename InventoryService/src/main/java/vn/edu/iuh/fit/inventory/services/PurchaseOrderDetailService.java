package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderDetailResponse;

import java.util.List;

public interface PurchaseOrderDetailService {
    // Lấy tất cả chi tiết phiếu mua hàng theo id phiếu mua hàng
    List<PurchaseOrderDetailResponse> getAllPurchaseOrderDetailByPurchaseOrderId(Long purchaseOrderId);

    void addReviewByCategory(Long purchaseOrderId, PurchaseOrderDetailRequest request);

    void addReviewByMedicine(Long purchaseOrderId, PurchaseOrderDetailRequest request);
}
