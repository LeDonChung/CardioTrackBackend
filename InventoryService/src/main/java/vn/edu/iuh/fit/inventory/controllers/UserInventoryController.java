package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.enums.UserInventoryRole;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserInventoryResponse;
import vn.edu.iuh.fit.inventory.services.UserInventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-inventory")
public class UserInventoryController {

    @Autowired
    private UserInventoryService userInventoryService;

    // verify
    @GetMapping("/permission")
    @PreAuthorize("hasAuthority('CONTROL_STAFF')")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("You have permission to access user");
    }

    // Lấy tất cả User
    @GetMapping
    public ResponseEntity<BaseResponse<List<UserInventoryResponse>>> getAllSuppliers() {
        List<UserInventoryResponse> user = userInventoryService.getAllUserInventory();
        return ResponseEntity.ok(
                BaseResponse
                        .<List<UserInventoryResponse>>builder()
                        .data(user)
                        .success(true)
                        .build()
        );
    }

    // Cập nhật vai trò của nhân viên kho
    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<UserInventoryResponse>> updateUserInventoryRole(
            @PathVariable("id") Long id,
            @RequestParam("role") UserInventoryRole role) {
        UserInventoryResponse userInventoryResponse = userInventoryService.updateUserInventoryRole(id, role);
        return ResponseEntity.ok(
                BaseResponse
                        .<UserInventoryResponse>builder()
                        .data(userInventoryResponse)
                        .success(true)
                        .build()
        );
    }
}
