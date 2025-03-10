package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.enums.InventoryImportStatus;
import vn.edu.iuh.fit.inventory.exceptions.InventoryImportException;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryImportRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportResponse;

public interface InventoryImportService {
    //Thêm phiếu nhập
    InventoryImportResponse save(InventoryImportRequest request) throws InventoryImportException;

    //Thay đổi trạng thái của phiếu nhập
    InventoryImportResponse changeStatus(Long id, InventoryImportStatus invenotryImport) throws InventoryImportException;
}
