package vn.edu.iuh.fit.product.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.exceptions.CategoryException;
import vn.edu.iuh.fit.product.model.dto.request.CategoryRequest;
import vn.edu.iuh.fit.product.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.product.model.dto.response.CategoryResponse;
import vn.edu.iuh.fit.product.model.entity.Category;
import vn.edu.iuh.fit.product.services.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Thêm - chỉnh sửa danh mục thuốc
    @PostMapping
    public ResponseEntity<BaseResponse<CategoryResponse>> addCategory(@RequestBody CategoryRequest request) throws CategoryException {
        CategoryResponse category = categoryService.save(request);
        return ResponseEntity.ok(
                BaseResponse
                        .<CategoryResponse>builder()
                        .data(category)
                        .success(true)
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<BaseResponse<CategoryResponse>> updateCategory(@RequestBody CategoryRequest request) throws CategoryException {
        CategoryResponse category = categoryService.save(request);
        return ResponseEntity.ok(
                BaseResponse
                        .<CategoryResponse>builder()
                        .data(category)
                        .success(true)
                        .build()
        );
    }

    //Tìm danh mục thuốc theo category_id
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) throws CategoryException {
        CategoryResponse category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<CategoryResponse>builder()
                        .data(category)
                        .success(true)
                        .build()
        );
    }

    //Danh sách các danh mục thuốc cha cấp 1
    @GetMapping("/get-category-level1")
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getParentCategories() {
        List<CategoryResponse> categories = categoryService.getAllParentCategories();
        return ResponseEntity.ok(
                BaseResponse
                        .<List<CategoryResponse>>builder()
                        .data(categories)
                        .success(true)
                        .build()
        );
    }

    //Danh sách các danh mục con theo parent_id
    @GetMapping("/get-by-parent-id/{parentId}")
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getChildCategories(@PathVariable Long parentId) {
        List<CategoryResponse> categories = categoryService.getAllChildCategories(parentId);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<CategoryResponse>>builder()
                        .data(categories)
                        .success(true)
                        .build()
        );
    }

    //Tìm Danh mục theo tên (title)
    @GetMapping("/get-by-title")
    public ResponseEntity<BaseResponse<List<CategoryResponse>>> getCategoryByTitle(@RequestParam String title) {
        List<CategoryResponse> categories = categoryService.getCategoryByTitle(title);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<CategoryResponse>>builder()
                        .data(categories)
                        .success(true)
                        .build()
        );
    }
}
