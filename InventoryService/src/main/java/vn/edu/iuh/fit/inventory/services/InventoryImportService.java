package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.enums.InventoryImportStatus;
import vn.edu.iuh.fit.inventory.exceptions.InventoryImportException;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryImportRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;

public interface InventoryImportService {
    //Thêm phiếu nhập
    InventoryImportResponse save(InventoryImportRequest request) throws InventoryImportException;

    //Thay đổi trạng thái của phiếu nhập
    InventoryImportResponse changeStatus(Long id, InventoryImportStatus invenotryImport) throws InventoryImportException;

    // Lấy tất cả phiếu nhập
    PageDTO<InventoryImportResponse> getPagesInventoryImport(int page, int size, String sortBy, String sortName);

    // Lấy tất cả đơn nhập theo status = PENDING
    PageDTO<InventoryImportResponse> getAllPendingImport(int page, int size, String sortBy, String sortName);
}
