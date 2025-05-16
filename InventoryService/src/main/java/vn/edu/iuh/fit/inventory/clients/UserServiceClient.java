package vn.edu.iuh.fit.inventory.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.models.dtos.requests.AddressRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.AddressResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserInventoryResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserResponse;

@FeignClient(name = "user-service", path = "/api/v1/user")
public interface UserServiceClient {

    @PostMapping("/address")
    ResponseEntity<BaseResponse<AddressResponse>> addAddress(@RequestBody AddressRequest request);

    @GetMapping("/get-by-id/{id}")
    ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable("id") Long id);

    @PutMapping("/update-user/{id}")
    ResponseEntity<BaseResponse<UserResponse>> updateUser(@PathVariable("id") Long id, @RequestBody UserResponse userInventoryRequest);

}
