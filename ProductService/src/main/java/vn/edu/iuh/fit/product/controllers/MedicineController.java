package vn.edu.iuh.fit.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.models.dtos.PageDTO;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineSearchRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.product.services.MedicineService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;
    //add - update medicine
    @PostMapping
    public ResponseEntity<BaseResponse<MedicineResponse>> saveMedicine(@RequestBody MedicineRequest medicineRequest) throws MedicineException {
        MedicineResponse medicineResponse = medicineService.save(medicineRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineResponse>builder()
                        .data(medicineResponse)
                        .success(true)
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<BaseResponse<MedicineResponse>> updateMedicine(@RequestBody MedicineRequest medicineRequest) throws MedicineException {
        MedicineResponse medicineResponse = medicineService.save(medicineRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineResponse>builder()
                        .data(medicineResponse)
                        .success(true)
                        .build()
        );
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BaseResponse<MedicineResponse>> updateStatusById(@PathVariable Long id, @PathVariable int status) throws MedicineException {
        MedicineResponse medicineResponse = medicineService.updateStatusById(id, status);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineResponse>builder()
                        .data(medicineResponse)
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MedicineResponse>> getById(@PathVariable Long id) throws MedicineException {
        MedicineResponse medicineResponse = medicineService.getMedicineById(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineResponse>builder()
                        .data(medicineResponse)
                        .success(true)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PageDTO<MedicineResponse>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(required = false) String sortBy,
                                                                            @RequestParam(required = false) String sorName) {
        PageDTO<MedicineResponse> pageDTO = medicineService.getPages(page, size, sortBy, sorName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<MedicineResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<BaseResponse<Boolean>> isExists(@PathVariable Long id) {
        boolean isExists = medicineService.isExists(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<Boolean>builder()
                        .data(isExists)
                        .success(true)
                        .build()
        );
    }

    @PostMapping("/search")
    public ResponseEntity<BaseResponse<PageDTO<MedicineResponse>>> search(@RequestBody MedicineSearchRequest request,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "16") int size,
                                                                          @RequestParam(defaultValue = "price") String sortBy,
                                                                          @RequestParam(defaultValue = "desc") String sortName
    ) {
        PageDTO<MedicineResponse> medicineResponses = medicineService.search(request, page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<MedicineResponse>>builder()
                        .data(medicineResponses)
                        .success(true)
                        .build()
        );
    }
}
