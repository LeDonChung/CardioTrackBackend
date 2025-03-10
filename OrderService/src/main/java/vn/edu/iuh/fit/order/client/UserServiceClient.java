package vn.edu.iuh.fit.order.client;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.order.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.order.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.order.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.order.model.dto.response.UserResponse;

import java.util.List;

@FeignClient(name = "user-service", path = "/api/v1/user")
public interface UserServiceClient {

    @PostMapping("/address")
    ResponseEntity<BaseResponse<AddressResponse>> addAddress(@RequestBody AddressRequest request);

    @GetMapping("/get-by-id/{id}")
    ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable("id") Long id);

    @GetMapping("/addresses/{id}")
    ResponseEntity<BaseResponse<List<AddressResponse>>> getUserAddresses(@PathVariable("id") Long id);

    @GetMapping("/address-byid/{addressId}")
    ResponseEntity<AddressResponse> getAddressById(@PathVariable Long addressId);
}
