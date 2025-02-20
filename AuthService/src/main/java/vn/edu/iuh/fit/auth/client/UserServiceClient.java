package vn.edu.iuh.fit.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.auth.model.dto.request.LoginRequest;
import vn.edu.iuh.fit.auth.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.auth.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.auth.model.dto.response.UserResponse;

@FeignClient(name = "user-service", path = "/api/v1/user")
public interface UserServiceClient {

    @PostMapping("/register")
    ResponseEntity<BaseResponse<UserResponse>> register(@RequestBody UserRegisterRequest request);

    @PostMapping("/validate-token")
    ResponseEntity<BaseResponse<UserResponse>> validateToken(@RequestParam String token);

    @PostMapping("/login")
    ResponseEntity<BaseResponse<Object>> login(@RequestBody LoginRequest loginRequest);
}
