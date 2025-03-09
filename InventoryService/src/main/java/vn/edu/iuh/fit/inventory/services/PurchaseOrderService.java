package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;

import java.util.List;

public interface PurchaseOrderService {

    // Lấy tất cả phiếu mua hàng theo status = PENDING
    List<PurchaseOrderResponse> getAllPendingPurchaseOrder();
}
