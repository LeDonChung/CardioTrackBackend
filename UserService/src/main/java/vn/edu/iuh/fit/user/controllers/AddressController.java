package vn.edu.iuh.fit.user.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.services.AddressService;
import vn.edu.iuh.fit.user.services.UserService;
import vn.edu.iuh.fit.user.model.dto.request.AddressRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping("/api/v1/address")
@RestController
@Slf4j
public class AddressController {
    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;
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

    //xóa  địa chỉ theo id
    @DeleteMapping("/delete-address/{id}")
    public ResponseEntity<BaseResponse<Boolean>> deleteAddress(@PathVariable("id") Long id) throws UserException {
        Boolean result = userService.deleteAddressById(id);
        return ResponseEntity.ok(
                BaseResponse.<Boolean>builder()
                        .data(result)
                        .code(HttpStatus.OK.name())
                        .success(true)
                        .build()
        );
    }
    //thêm địa chỉ mới cho user
    @PostMapping("/add-address")
    public ResponseEntity<BaseResponse<AddressResponse>> addAddress(@RequestBody AddressRequest address)throws UserException {
        AddressResponse addressResponse = userService.addAddress(address);
        return ResponseEntity.ok(
                BaseResponse.<AddressResponse>builder()
                        .code(String.valueOf(HttpStatus.OK.value()))
                        .success(true)
                        .data(addressResponse)
                        .build()
        );
    }

    //update địa chỉ theo id address_detail
    @PutMapping("/update-address/{id}")
    public ResponseEntity<BaseResponse<AddressResponse>> updateAddress(@PathVariable("id") Long id, @RequestBody AddressRequest address) throws UserException {
        AddressResponse addressResponse = userService.updateAddressById(id, address);
        return ResponseEntity.ok(
                BaseResponse.<AddressResponse>builder()
                        .code(String.valueOf(HttpStatus.OK.value()))
                        .success(true)
                        .data(addressResponse)
                        .build()
        );
    }
  
  @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<List<AddressResponse>>> findAddressByUserId(@PathVariable Long userId) {
        log.info("Get address by user id: {}", userId);
        List<AddressResponse> addressResponses = addressService.findAddressByUserId(userId);
        return ResponseEntity.ok(new BaseResponse<>(addressResponses));
    }

}


    
