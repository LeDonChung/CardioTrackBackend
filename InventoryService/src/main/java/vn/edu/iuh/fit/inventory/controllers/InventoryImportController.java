package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.enums.InventoryImportStatus;
import vn.edu.iuh.fit.inventory.exceptions.InventoryImportException;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryImportRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportResponse;
import vn.edu.iuh.fit.inventory.services.InventoryImportService;

@RestController
@RequestMapping("/api/v1/inventory-import")
public class InventoryImportController {
    @Autowired
    private InventoryImportService inventoryImportService;

    // Thêm phiếu nhập
    @PostMapping
    public ResponseEntity<BaseResponse<InventoryImportResponse>> addOrder(@RequestBody InventoryImportRequest request) throws InventoryImportException {
        InventoryImportResponse inventoryImport = inventoryImportService.save(request);
        return ResponseEntity.ok(
                BaseResponse
                        .<InventoryImportResponse>builder()
                        .data(inventoryImport)
                        .success(true)
                        .build()
        );
    }


    // Thay đổi trạng thái của phiếu nhập
    @PutMapping("/change-status/{id}")
    public ResponseEntity<BaseResponse<InventoryImportResponse>> changeStatus(@PathVariable Long id, @RequestParam InventoryImportStatus status) throws InventoryImportException {
        InventoryImportResponse inventoryImport = inventoryImportService.changeStatus(id, status);
        return ResponseEntity.ok(
                BaseResponse
                        .<InventoryImportResponse>builder()
                        .data(inventoryImport)
                        .success(true)
                        .build()
        );
    }

    // Lấy tất cả phiếu nhập
    @GetMapping
    public ResponseEntity<BaseResponse<PageDTO<InventoryImportResponse>>> getPagesInventoryImport(@RequestParam(defaultValue = "1") int page,
                                                                                                  @RequestParam(defaultValue = "10") int size,
                                                                                                  @RequestParam(required = false) String sortBy,
                                                                                                  @RequestParam(required = false) String sortName) {
        PageDTO<InventoryImportResponse> pageDTO = inventoryImportService.getPagesInventoryImport(page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<InventoryImportResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }
}
