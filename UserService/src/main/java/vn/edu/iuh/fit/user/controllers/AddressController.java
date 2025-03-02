package vn.edu.iuh.fit.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.user.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.services.UserService;

import java.util.List;

@RequestMapping("/api/v1/address")
@RestController
public class AddressController {
    @Autowired
    private UserService userService;
    //tìm địa chỉ user
    @GetMapping("/get-by-user-id/{id}")
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
}
