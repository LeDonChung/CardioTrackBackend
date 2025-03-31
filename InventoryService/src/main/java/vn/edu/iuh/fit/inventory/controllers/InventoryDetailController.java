package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.exceptions.InventoryDetailException;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.CategoryResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryDetailResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.inventory.services.InventoryDetailService;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryDetailController {
    @Autowired
    private InventoryDetailService inventoryDetailService;

    //Get all inventory_detail
    @GetMapping
    public ResponseEntity<BaseResponse<PageDTO<InventoryDetailResponse>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "10") int size,
                                                                                   @RequestParam(required = false) String sortBy,
                                                                                   @RequestParam(required = false) String sorName) {
        PageDTO<InventoryDetailResponse> pageDTO = inventoryDetailService.getPagesInventoryDetail(page, size, sortBy, sorName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<InventoryDetailResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }

    //Get inventory_detail by id
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<InventoryDetailResponse>> getById(@PathVariable Long id) {
        InventoryDetailResponse inventoryDetailResponse = inventoryDetailService.getMedicineById(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<InventoryDetailResponse>builder()
                        .data(inventoryDetailResponse)
                        .success(true)
                        .build()
        );
    }

    //Get medicine_detail from product-service
    @GetMapping("/medicine-detail/{id}")
    public ResponseEntity<BaseResponse<MedicineResponse>> getMedicineDetails(@PathVariable Long id) {
        MedicineResponse medicineResponse = inventoryDetailService.getMedicineDetails(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineResponse>builder()
                        .data(medicineResponse)
                        .success(true)
                        .build()
        );
    }

    //Get category_detail from product-service
    @GetMapping("/category-detail/{id}")
    public ResponseEntity<BaseResponse<CategoryResponse>> getCategoryDetails(@PathVariable Long id) {
        CategoryResponse categoryResponse = inventoryDetailService.getCategoryDetails(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<CategoryResponse>builder()
                        .data(categoryResponse)
                        .success(true)
                        .build()
        );
    }

    //Add inventory_detail
    @PostMapping
    public ResponseEntity<BaseResponse<InventoryDetailResponse>> save(@RequestBody InventoryDetailRequest inventoryDetailRequest) throws InventoryDetailException {
        InventoryDetailResponse inventoryDetailResponse = inventoryDetailService.save(inventoryDetailRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<InventoryDetailResponse>builder()
                        .data(inventoryDetailResponse)
                        .success(true)
                        .build()
        );
}
    //Get medicine by category
    @GetMapping("/medicine-by-category/{id}")
    public ResponseEntity<BaseResponse<PageDTO<InventoryDetailResponse>>> getMedicineByCategory(@PathVariable Long id,
                                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                                 @RequestParam(defaultValue = "10") int size,
                                                                                                 @RequestParam(required = false) String sortBy,
                                                                                                 @RequestParam(required = false) String sortName) {
        PageDTO<InventoryDetailResponse> pageDTO = inventoryDetailService.getMedicineByCategory(id, page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<InventoryDetailResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/total-quantity")
    public Long getTotalQuantity() {
        return inventoryDetailService.getTotalQuantity();
    }

    @GetMapping("/total-quantity-medicine/{medicineId}")
    public Long getTotalQuantityMedicine(@PathVariable Long medicineId) {
        return inventoryDetailService.getTotalQuantityMedicine(medicineId);
    }

    @PutMapping("/update-quantity-medicine/{medicineId}/{quantity}")
    public ResponseEntity<BaseResponse<Integer>> updateQuantityByMedicine(@PathVariable Long medicineId, @PathVariable Long quantity) {
        int result = inventoryDetailService.updateQuantityByMedicine(medicineId, quantity);
        return ResponseEntity.ok(
                BaseResponse
                        .<Integer>builder()
                        .data(result)
                        .success(true)
                        .build()
        );
    }


    @GetMapping("/medicines-near-expiration")
    public ResponseEntity<BaseResponse<PageDTO<InventoryDetailResponse>>> getMedicinesNearExpiration(@RequestParam(defaultValue = "0") int page,
                                                                                                     @RequestParam(defaultValue = "10") int size,
                                                                                                     @RequestParam(required = false) String sortBy,
                                                                                                     @RequestParam(required = false) String sortName) {
        PageDTO<InventoryDetailResponse> pageDTO = inventoryDetailService.getMedicinesNearExpiration(page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<InventoryDetailResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/medicines-expired")
    public ResponseEntity<BaseResponse<PageDTO<InventoryDetailResponse>>> getMedicinesExpired(@RequestParam(defaultValue = "0") int page,
                                                                                              @RequestParam(defaultValue = "10") int size,
                                                                                              @RequestParam(required = false) String sortBy,
                                                                                              @RequestParam(required = false) String sortName) {
        PageDTO<InventoryDetailResponse> pageDTO = inventoryDetailService.getMedicinesExpired(page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<InventoryDetailResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/inventory-details-expiration")
    public ResponseEntity<BaseResponse<PageDTO<InventoryDetailResponse>>> getInventoryDetailsExpiration(
                                                                                                         @RequestParam(defaultValue = "0") int page,
                                                                                                         @RequestParam(defaultValue = "10") int size,
                                                                                                         @RequestParam(required = false) String sortBy,
                                                                                                         @RequestParam(required = false) String sortName,
                                                                                                         @RequestParam(required = false) Long medicineId) {
        PageDTO<InventoryDetailResponse> pageDTO = inventoryDetailService.getInventoryDetailsExpiration(page, size, sortBy, sortName, medicineId);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<InventoryDetailResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }
}
