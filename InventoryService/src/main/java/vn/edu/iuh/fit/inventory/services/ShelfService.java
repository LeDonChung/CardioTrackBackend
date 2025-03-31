package vn.edu.iuh.fit.inventory.services;

import org.springframework.security.core.parameters.P;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.ShelfRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

import java.util.List;

public interface ShelfService {
    PageDTO<ShelfResponse> getPagesShelf(int page, int size, String sortBy, String sortName);

    ShelfResponse save(ShelfRequest request);

    ShelfResponse getShelfById(Long id);

    void deleteShelfById(Long id);

    //hiển thị thông tin của các shelf có capacity - totalProduct lớn hơn một giá trị nhập từ bàn phím
    List<ShelfResponse> findShelfsWithCapacityGreaterThan(int threshold);

    //Cập nhật (thêm) số lượng sản phẩm của shelf khi nhập hàng hoặc hủy đơn(thêm lại vào kệ)
    void updateTotalProduct(Long id, int quantity);

    // Cập nhật (trừ) số lượng sản phẩm của shelf khi đặt hàng
    void updateSubtractTotalProduct(Long id, int quantity);

    PageDTO<ShelfResponse> findLocationPage(int page, int size, String sortBy, String sortName, String location);

}
