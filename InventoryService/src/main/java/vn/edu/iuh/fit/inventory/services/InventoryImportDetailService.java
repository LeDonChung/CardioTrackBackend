package vn.edu.iuh.fit.inventory.services;

import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportDetailResponse;

import java.util.List;

public interface InventoryImportDetailService {
    // Lấy tất cả chi tiết phiếu nhập theo inventoryImportId
    List<InventoryImportDetailResponse> getAllPInventoryImportDetailByImportId(Long inventoryImportId);
}
