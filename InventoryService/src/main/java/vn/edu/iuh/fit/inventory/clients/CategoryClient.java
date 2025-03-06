package vn.edu.iuh.fit.inventory.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.CategoryResponse;

@FeignClient(name = "product-service", path = "/api/v1/category")
public interface CategoryClient {

    // Gọi product-service từ xa để lấy tất cả thông tin của category
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponse>> getCategoryById(@PathVariable Long id);
}
