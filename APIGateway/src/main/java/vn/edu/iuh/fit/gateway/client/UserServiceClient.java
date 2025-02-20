package vn.edu.iuh.fit.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.gateway.model.response.BaseResponse;
import vn.edu.iuh.fit.gateway.model.response.UserResponse;

@FeignClient(name = "user-service", path = "/api/v1/user")
public interface UserServiceClient {

    @PostMapping("/validate-token")
    ResponseEntity<BaseResponse<UserResponse>> validateToken(@RequestParam String token);

}
