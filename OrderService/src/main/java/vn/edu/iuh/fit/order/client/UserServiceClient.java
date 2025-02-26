package vn.edu.iuh.fit.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.order.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.order.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.order.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.order.model.dto.response.UserResponse;

@FeignClient(name = "user-service", path = "/api/v1/user")
public interface UserServiceClient {

    @PostMapping("/address")
    ResponseEntity<BaseResponse<AddressResponse>> addAddress(@RequestBody AddressRequest request);

    @GetMapping("/get-by-id/{id}")
    ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable("id") Long id);
}
