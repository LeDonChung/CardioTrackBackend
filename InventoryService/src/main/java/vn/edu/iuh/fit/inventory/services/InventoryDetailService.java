package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.exceptions.InventoryDetailException;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryDetailRequest;
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

    //Thêm một thuốc vào kho
    InventoryDetailResponse save(InventoryDetailRequest request) throws InventoryDetailException;
    //lấy danh sách medicine theo category id
    PageDTO<InventoryDetailResponse> getMedicineByCategory(Long id, int page, int size, String sortBy, String sortName);

    //Tổng số lượng thuốc trong kho
    Long getTotalQuantity();

//    //Cập nhật (thêm) số lượng của một thuốc khi hủy đơn (thêm lại vào kho)
//    void updateAddTotalProduct(Long medicineId, int quantity);
//
//    //Cập nhật (trừ) số lượng của một thuốc trong kho khi đặt hàng
//    void updateSubtractTotalProduct(Long medicineId, int quantity);

    // Tìm chi tiết kho theo medicine và shelfId
    InventoryDetailResponse findInventoryDetailByMedicineAndShelf(Long medicineId, Long shelfId);

    //Tìm tổng số lượng của 1 thuốc trong kho (1 thuốc có thể nằm trên nhiều kệ)
}
