package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.enums.PurchaseOrderStatus;
import vn.edu.iuh.fit.inventory.exceptions.PurchaseOrderException;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;

import java.util.List;

public interface PurchaseOrderService {

    // Lấy tất cả phiếu mua hàng theo status = PENDING
    List<PurchaseOrderResponse> getAllPendingPurchaseOrder();

    //Thay đổi status của phiếu mua hàng
    PurchaseOrderResponse changeStatus(Long id, PurchaseOrderStatus status) throws PurchaseOrderException;
}
