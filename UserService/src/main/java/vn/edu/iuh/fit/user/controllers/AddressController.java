package vn.edu.iuh.fit.user.controllers;

import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.user.services.AddressService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@Slf4j
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/{userId}")
    public ResponseEntity<BaseResponse<List<AddressResponse>>> findAddressByUserId(@PathVariable Long userId) {
        log.info("Get address by user id: {}", userId);
        List<AddressResponse> addressResponses = addressService.findAddressByUserId(userId);
        return ResponseEntity.ok(new BaseResponse<>(addressResponses));
    }
}
