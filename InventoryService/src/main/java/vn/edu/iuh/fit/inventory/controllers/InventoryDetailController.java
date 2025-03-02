package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
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
}
