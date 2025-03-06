package vn.edu.iuh.fit.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.iuh.fit.order.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.order.model.dto.response.ProductResponse;

@FeignClient(name = "product-service", path = "/api/v1/medicine")
public interface ProductServiceClient {

    @GetMapping("/exists/{id}")
    public ResponseEntity<BaseResponse<Boolean>> isExists(@PathVariable Long id);

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> getById(@PathVariable Long id);
}
