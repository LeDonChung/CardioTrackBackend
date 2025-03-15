package vn.edu.iuh.fit.user.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.request.UserUpdateRequest;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.model.entity.Address;
import vn.edu.iuh.fit.user.services.UserService;

import java.util.List;
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


//        if(!userService.verifyOtp(request.getUsername(), request.getOtp())){
//            throw new UserException(SystemConstraints.PLS_VERIFY_OTP);
//        }


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
        System.out.println("🔍 Nhận request validate token: " + token); // ✅ Debug log
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

    @GetMapping("/info")
    public ResponseEntity<BaseResponse<UserResponse>> getInfo(@RequestParam("token") String token) {
        System.out.println("🔍 Nhận request validate token: " + token); // ✅ Debug log
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

    @PostMapping("/generation-otp")
    public ResponseEntity<BaseResponse<Boolean>> generationOtp(@RequestParam("phoneNumber") String phoneNumber) throws UserException {
        Boolean result = userService.sendOtp(phoneNumber);
        return ResponseEntity.ok(
                BaseResponse
                        .<Boolean>builder()
                        .data(result)
                        .code(HttpStatus.OK.name())
                        .success(true)
                        .build()
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<BaseResponse<Boolean>> verifyOtp(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("otp") String otp) throws UserException {
        Boolean result = userService.verifyOtp(phoneNumber, otp);
        return ResponseEntity.ok(
                BaseResponse
                        .<Boolean>builder()
                        .data(result)
                        .code(HttpStatus.OK.name())
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/find-id-by-phone-number")
    public ResponseEntity<BaseResponse<Long>> findIdByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        Long result = userService.findIdByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(
                BaseResponse
                        .<Long>builder()
                        .data(result)
                        .code(HttpStatus.OK.name())
                        .success(true)
                        .build()
        );
    }
    @PostMapping("/address")
    public ResponseEntity<BaseResponse<AddressResponse>> addAddress(@RequestBody AddressRequest address) throws UserException {
        AddressResponse a = userService.addAddress(address);
        return new ResponseEntity<>(
                BaseResponse
                        .<AddressResponse>builder()
                        .code(String.valueOf(HttpStatus.OK.value()))
                        .success(true)
                        .data(a)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable("id") Long id) {
        UserResponse user = userService.getUserById(id);
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
//tìm địa chỉ user
@GetMapping("/addresses/{id}")
public ResponseEntity<BaseResponse<List<AddressResponse>>> getUserAddresses(@PathVariable("id") Long id) {
    List<AddressResponse> addresses = userService.getAddressesByUserId(id);
    return ResponseEntity.ok(
            BaseResponse.<List<AddressResponse>>builder()
                    .code(String.valueOf(HttpStatus.OK.value()))
                    .success(true)
                    .data(addresses)
                    .build()
    );
}


    //hàm update user by id
    @PutMapping("/update-user/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest userUpdateRequest) throws UserException {
        UserResponse user = userService.updateUserById(id, userUpdateRequest);
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

    @GetMapping("/address-byid/{addressId}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long addressId) throws UserException {
        AddressResponse address = userService.getAddressById_Address(addressId);
        return new ResponseEntity<>(
                address,
                HttpStatus.OK
        );
    }

}
