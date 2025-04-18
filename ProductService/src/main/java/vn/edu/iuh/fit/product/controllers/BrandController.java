package vn.edu.iuh.fit.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.exceptions.BrandException;
import vn.edu.iuh.fit.product.models.dtos.PageDTO;
import vn.edu.iuh.fit.product.models.dtos.requests.BrandRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.BrandResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.product.models.entities.Medicine;
import vn.edu.iuh.fit.product.services.BrandService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    //Thêm - chỉnh sửa thương hiệu
    @PostMapping
    public ResponseEntity<BaseResponse<BrandResponse>> addCategory(@RequestBody BrandRequest request) throws BrandException {
        BrandResponse category = brandService.save(request);
        return ResponseEntity.ok(
                BaseResponse
                        .<BrandResponse>builder()
                        .data(category)
                        .success(true)
                        .build()
        );
    }

    //Xóa thương hiệu
    //Tìm thương hiệu theo brand_id
    //Danh sách thương hiệu
    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse<List<BrandResponse>>> getAllBrand() throws BrandException {
        List<BrandResponse> brands = brandService.getAllBrand();
        return ResponseEntity.ok(
                BaseResponse
                        .<List<BrandResponse>>builder()
                        .data(brands)
                        .success(true)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<PageDTO<BrandResponse>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(required = false) String sortBy,
                                                                       @RequestParam(required = false) String sortName) {
        PageDTO<BrandResponse> brandResponses = brandService.getAll(page, size, sortBy, sortName);



        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<BrandResponse>>builder()
                        .data(brandResponses)
                        .success(true)
                        .build()
        );
    }
}
