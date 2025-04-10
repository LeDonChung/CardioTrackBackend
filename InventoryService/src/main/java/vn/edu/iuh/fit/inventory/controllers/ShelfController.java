package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.inventory.exceptions.ShelfException;
import vn.edu.iuh.fit.inventory.mappers.ShelfMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.ShelfRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
import vn.edu.iuh.fit.inventory.services.ShelfService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/shelf")
public class ShelfController {
    @Autowired
    private ShelfService shelfService;

    @GetMapping
    public ResponseEntity<BaseResponse<PageDTO<ShelfResponse>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam(required = false) String sortBy,
                                                                         @RequestParam(required = false) String sortName,
                                                                         @RequestParam(required = false) String location) {
        PageDTO<ShelfResponse> pageDTO = shelfService.getPagesShelf(page, size, sortBy, sortName, location);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<ShelfResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }

    //add shelf
    @PostMapping
    public ResponseEntity<BaseResponse<ShelfResponse>> save(@RequestBody ShelfRequest shelfRequest) throws ShelfException {
        ShelfResponse shelfResponse = shelfService.save(shelfRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<ShelfResponse>builder()
                        .data(shelfResponse)
                        .success(true)
                        .build()
        );
    }
    // Update shelf
    @PutMapping
    public ResponseEntity<BaseResponse<ShelfResponse>> update(@RequestBody ShelfRequest shelfRequest) throws ShelfException {
        ShelfResponse shelfResponse = shelfService.save(shelfRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<ShelfResponse>builder()
                        .data(shelfResponse)
                        .success(true)
                        .build()
        );
    }

    // Get shelf by id
    @GetMapping("/id")
    public ResponseEntity<BaseResponse<ShelfResponse>> getShelfById(@RequestParam Long id) {
        ShelfResponse shelfResponse = shelfService.getShelfById(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<ShelfResponse>builder()
                        .data(shelfResponse)
                        .success(true)
                        .build()
        );
    }

    // Delete shelf by id
    @GetMapping("/delete")
    public ResponseEntity<BaseResponse<String>> deleteShelfById(@RequestParam Long id) {
        shelfService.deleteShelfById(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<String>builder()
                        .data("Delete shelf success")
                        .success(true)
                        .build()
        );
    }

    //hiển thị thông tin của các shelf có capacity - totalProduct lớn hơn một giá trị nhập từ bàn phím
    @GetMapping("/findShelfsWithCapacityGreaterThan")
    public ResponseEntity<BaseResponse<List<ShelfResponse>>> findShelfsWithCapacityGreaterThan(@RequestParam int threshold) {
        List<ShelfResponse> pageDTO = shelfService.findShelfsWithCapacityGreaterThan(threshold);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<ShelfResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }
    // Get shelf by location
    @GetMapping("/location")
    public ResponseEntity<BaseResponse<PageDTO<ShelfResponse>>> findLocationPage(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(required = false) String sortBy,
                                                                                @RequestParam(required = false) String sortName,
                                                                                @RequestParam(required = false) String location) {
        PageDTO<ShelfResponse> pageDTO = shelfService.findLocationPage(page, size, sortBy, sortName, location);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<ShelfResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }

    //Cập nhật (thêm) số lượng sản phẩm của shelf khi nhập hàng hoặc hủy đơn(thêm lại vào kệ)
    @PutMapping("/update-add-total-product")
    public ResponseEntity<BaseResponse<String>> updateAddTotalProduct(@RequestParam Long id, @RequestParam int quantity) {
        shelfService.updateTotalProduct(id, quantity);
        return ResponseEntity.ok(
                BaseResponse
                        .<String>builder()
                        .data("Update total product success")
                        .success(true)
                        .build()
        );
    }

    // Cập nhật (trừ) số lượng sản phẩm của shelf khi đặt hàng
    @PutMapping("/update-subtract-total-product")
    public ResponseEntity<BaseResponse<String>> updateSubtractTotalProduct(@RequestParam Long id, @RequestParam int quantity) {
        shelfService.updateSubtractTotalProduct(id, quantity);
        return ResponseEntity.ok(
                BaseResponse
                        .<String>builder()
                        .data("Update total product success")
                        .success(true)
                        .build()
        );
    }
}
