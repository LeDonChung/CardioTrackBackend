package vn.edu.iuh.fit.inventory.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.inventory.models.dtos.responses.*;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;

@FeignClient(name = "product-service", path = "/api/v1/medicine")
public interface ProductServiceClient {

    // Nối với product-serice từ xa để kiểm tra cái Long medicine trong đây có thực sự tồn tại
    @GetMapping("/exists/{id}")
    public ResponseEntity<BaseResponse<Boolean>> isExists(@PathVariable Long id);

    // Gọi product-service từ xa để lấy tất cả thông tin của medicine
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MedicineResponse>> getMedicineById(@PathVariable Long id);
}
