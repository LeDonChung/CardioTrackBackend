package vn.edu.iuh.fit.healthcheck.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.healthcheck.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserResponse;


@FeignClient(name = "user-service", path = "/api/v1/user")
public interface UserServiceClient {

    @GetMapping("/find-id-by-phone-number")
    ResponseEntity<BaseResponse<Long>> findIdByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);

    @GetMapping("/get-by-id/{id}")
    ResponseEntity<BaseResponse<UserResponse>> findUserById(@PathVariable Long id);
}
