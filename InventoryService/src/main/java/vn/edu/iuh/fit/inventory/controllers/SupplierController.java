package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.models.dtos.requests.SupplierRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.SupplierResponse;
import vn.edu.iuh.fit.inventory.services.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // Get all suppliers
    @GetMapping
    public ResponseEntity<BaseResponse<List<SupplierResponse>>> getAllSuppliers() {
        List<SupplierResponse> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(
                BaseResponse
                        .<List<SupplierResponse>>builder()
                        .data(suppliers)
                        .success(true)
                        .build()
        );
    }

    // add supplier
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<SupplierResponse>> save(SupplierRequest supplierRequest) {
        SupplierResponse supplierResponse = supplierService.save(supplierRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<SupplierResponse>builder()
                        .data(supplierResponse)
                        .success(true)
                        .build()
        );
    }

    // get supplier by id
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<SupplierResponse>> getSupplierById(@PathVariable Long id) {
        SupplierResponse supplierResponse = supplierService.getSupplierById(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<SupplierResponse>builder()
                        .data(supplierResponse)
                        .success(true)
                        .build()
        );
    }
    // get supplier by name
    @GetMapping("/getSupplierByName")
    public ResponseEntity<BaseResponse<List<SupplierResponse>>> getSupplierByName(@RequestParam String name) {
        List<SupplierResponse> supplierResponse = supplierService.getSupplierByName(name);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<SupplierResponse>>builder()
                        .data(supplierResponse)
                        .success(true)
                        .build()
        );
    }
}
