package vn.edu.iuh.fit.auth.controller;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import vn.edu.iuh.fit.auth.client.UserServiceClient;
import vn.edu.iuh.fit.auth.exceptions.ErrorDetail;
import vn.edu.iuh.fit.auth.exceptions.UserException;
import vn.edu.iuh.fit.auth.model.dto.request.LoginRequest;
import vn.edu.iuh.fit.auth.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.auth.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.auth.model.dto.response.UserResponse;
import vn.edu.iuh.fit.auth.utils.SystemConstraints;

import java.time.LocalDateTime;

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
    @CircuitBreaker(name = "userLoginCircuit", fallbackMethod = "loginFallback")
    public ResponseEntity<BaseResponse<Object>> loginUser(@RequestBody LoginRequest request) {
        System.out.println("🔍 Nhận request loginX: " + request); // ✅ Debug log
        return ResponseEntity.ok(
                userServiceClient.login(request).getBody()
        );
    }

    ResponseEntity<ErrorDetail> loginFallback(LoginRequest request, Throwable t) throws UserException {
        System.out.println("🔍 Nhận request login: " + request);
        System.out.println("❌ Lỗi khi gọi đến User Service: " + t.getClass().getName() + ": " + t.getMessage());

        if (t instanceof FeignException feignException) {
            if (feignException.status() == 401) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(BaseResponse.builder()
//                                .success(false)
//                                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
//                                .data(SystemConstraints.INVALID_USERNAME_OR_PASSWORD)
//                                .build());
                throw new UserException(SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
            } else if (feignException.status() >= 500) {
//                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                        .body(BaseResponse.builder()
//                                .success(false)
//                                .code(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()))
//                                .data(SystemConstraints.USER_SERVICE_UNAVAILABLE)
//                                .build());
                throw new UserException(SystemConstraints.USER_SERVICE_UNAVAILABLE);
            }
        } else if (t instanceof CallNotPermittedException) {
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
//                    .body(BaseResponse.builder()
//                            .success(false)
//                            .code(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()))
//                            .data(SystemConstraints.USER_SERVICE_UNAVAILABLE)
//                            .build());
            throw new UserException(SystemConstraints.USER_SERVICE_UNAVAILABLE);
        }

//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(BaseResponse.builder()
//                        .success(false)
//                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
//                        .data(SystemConstraints.USER_SERVICE_UNAVAILABLE)
//                        .build());
        throw new UserException(SystemConstraints.USER_SERVICE_UNAVAILABLE);
    }





    @PostMapping("/generation-otp")
    public ResponseEntity<BaseResponse<Boolean>> generationOtp(@RequestParam("phoneNumber") String phoneNumber) {
        return userServiceClient.generationOtp(phoneNumber);
    }
}
