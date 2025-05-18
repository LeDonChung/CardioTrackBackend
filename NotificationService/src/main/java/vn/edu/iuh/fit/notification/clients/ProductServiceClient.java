package vn.edu.iuh.fit.notification.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.notification.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.notification.model.dto.response.MedicineResponse;

@FeignClient(name = "product-service", path = "/api/v1/medicine")
public interface ProductServiceClient {
    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<MedicineResponse>> getById(@PathVariable Long id);
}
