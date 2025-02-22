package vn.edu.iuh.fit.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.auth.client.UserServiceClient;
import vn.edu.iuh.fit.auth.exceptions.UserException;
import vn.edu.iuh.fit.auth.model.dto.request.LoginRequest;
import vn.edu.iuh.fit.auth.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.auth.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.auth.model.dto.response.UserResponse;
import vn.edu.iuh.fit.auth.utils.SystemConstraints;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserServiceClient userServiceClient;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@RequestBody UserRegisterRequest request) throws UserException {
        return userServiceClient.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Object>> loginUser(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userServiceClient.login(request).getBody()
        );
    }

    @PostMapping("/generation-otp")
    public ResponseEntity<BaseResponse<Boolean>> generationOtp(@RequestParam("phoneNumber") String phoneNumber) {
        return userServiceClient.generationOtp(phoneNumber);
    }
}
