package vn.edu.iuh.fit.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.product.exceptions.BrandException;
import vn.edu.iuh.fit.product.models.dtos.requests.BrandRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.BrandResponse;
import vn.edu.iuh.fit.product.services.BrandService;

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
}
