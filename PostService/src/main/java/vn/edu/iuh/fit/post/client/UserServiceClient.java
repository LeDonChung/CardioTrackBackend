package vn.edu.iuh.fit.post.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.post.model.dto.response.BaseResponse;

@FeignClient(name = "user-service", path = "/api/v1/user")
public interface UserServiceClient {

    @GetMapping("/find-id-by-phone-number")
    ResponseEntity<BaseResponse<Long>> findIdByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber);


}
