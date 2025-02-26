package vn.edu.iuh.fit.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.iuh.fit.order.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.order.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.order.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.order.model.dto.response.UserResponse;

@FeignClient(name = "product-service", path = "/api/v1/medicine")
public interface ProductServiceClient {

    @GetMapping("/exists/{id}")
    public ResponseEntity<BaseResponse<Boolean>> isExists(@PathVariable Long id);
}
