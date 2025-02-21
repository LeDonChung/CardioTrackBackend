package vn.edu.iuh.fit.user.controllers;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.jwt.JwtService;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.services.UserService;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@RequestBody UserRegisterRequest request) throws UserException {
        log.info("Register user: " + request);
        // Kiểm tra mật khẩu và xác nhận mật khẩu có trùng nhau không
        if (!Objects.equals(request.getPassword(), request.getRePassword())) {
            throw new UserException("Mật khẩu và xác nhận mật khẩu không trùng khớp.");
        }

        UserResponse result = userService.register(request);

        return ResponseEntity.ok(
                BaseResponse
                        .<UserResponse>builder()
                        .data(result)
                        .code(HttpStatus.OK.name())
                        .success(true)
                        .build()
        );
    }

    @PostMapping("/validate-token")
    public ResponseEntity<BaseResponse<UserResponse>> validationToken(@RequestParam("token") String token) {
        UserResponse user = userService.getMe(token);
        return new ResponseEntity<>(
                BaseResponse
                        .<UserResponse>builder()
                        .code(String.valueOf(HttpStatus.OK.value()))
                        .success(true)
                        .data(user)
                        .build(),
                HttpStatus.OK
        );
    }

}
