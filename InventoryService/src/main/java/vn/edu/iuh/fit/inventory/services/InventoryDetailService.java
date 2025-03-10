package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.exceptions.InventoryDetailException;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.CategoryResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryDetailResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.MedicineResponse;

public interface InventoryDetailService {
    // Lây danh sách thuốc trong kho - phân trang
    PageDTO<InventoryDetailResponse> getPagesInventoryDetail(int page, int size, String sortBy, String sortName);

    // Tìm thuốc trong kho theo id
    InventoryDetailResponse getMedicineById(Long id) throws InventoryDetailException;

    // Lấy thông tin chi tiết của thuốc từ prodcuct-service
    MedicineResponse getMedicineDetails(Long id);

    // Lấy thông tin chi tiết của danh mục từ product-service
    CategoryResponse getCategoryDetails(Long id);

    // Lấy số lượng sản phẩm theo categoryId
    int getQuantityCategoryProductInventory(Long categoryId);
}
