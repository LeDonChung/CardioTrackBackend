package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.enums.PurchaseOrderStatus;
import vn.edu.iuh.fit.inventory.exceptions.PurchaseOrderException;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;

import java.util.List;

public interface PurchaseOrderService {

    // Lấy tất cả phiếu mua hàng
    PageDTO<PurchaseOrderResponse> getAllPurchaseOrder(int page, int size, String sortBy, String sortName);

    // Thêm mới phiếu mua hàng
    PurchaseOrderResponse save(PurchaseOrderRequest request) throws PurchaseOrderException;

    // Lấy tất cả phiếu mua hàng theo status = PENDING
    List<PurchaseOrderResponse> getAllPendingPurchaseOrder();

    //Thay đổi status của phiếu mua hàng
    PurchaseOrderResponse changeStatus(Long id, PurchaseOrderStatus status) throws PurchaseOrderException;
}
