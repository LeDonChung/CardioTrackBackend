package vn.edu.iuh.fit.product.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.model.entities.Category;
import vn.edu.iuh.fit.product.services.CategoryService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Thêm - chỉnh sửa danh mục thuốc
    @PostMapping("/addCategory")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addCategory(@RequestBody Map<String, Object> requestData) {
        // Lấy `category` từ requestData và convert sang object Category
        ObjectMapper objectMapper = new ObjectMapper();
        Category category = objectMapper.convertValue(requestData, Category.class);

        // Lấy `parent_id` nếu có
        Long parent_id = (requestData.get("parent_id") != null) ? Long.valueOf(requestData.get("parent_id").toString()) : null;

        System.out.println("parent_id: " + parent_id);
        System.out.println("category: " + category);

        if(parent_id != null){
            Category categoryParent = categoryService.getCategoryById(parent_id);
            if (categoryParent != null) {
                category.setParent(categoryParent);
            } else {
                System.out.println("Category parent with ID " + parent_id + " not found!");
            }
        }
        categoryService.addCategory(category);
    }

    //Tìm danh mục thuốc theo id
    @GetMapping("/getCategoryById/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        System.out.println("Category of "+ id + ": " + category);
        return ResponseEntity.ok(category);
    }

    //Danh sách các danh mục thuốc cha cấp 1
    @GetMapping("/getParentCategories_level1")
    public ResponseEntity<List<Category>> getParentCategories() {
        List<Category> categories = categoryService.getAllParentCategories();
        System.out.println("AllParentCategories: " + categories);
        return ResponseEntity.ok(categories);
    }

    //Danh sách các danh mục con theo parent_id
    @GetMapping("/getChildCategories/{parent_id}")
    public ResponseEntity<List<Category>> getChildCategories(@PathVariable Long parent_id) {
        List<Category> categories = categoryService.getAllChildCategories(parent_id);
        System.out.println("ChildCategories of " + parent_id + ": " + categories);
        return ResponseEntity.ok(categories);
    }

    //Tìm Danh mục theo tên (title)
    @GetMapping("/getCategoryByTitle/{title}")
    public ResponseEntity<Category> getCategoryByTitle(@PathVariable String title) {
        Category category = categoryService.findCategoryByTitle(title);
        System.out.println("Category of "+ title + ": " + category);
        return ResponseEntity.ok(category);
    }

    //Xóa danh mục thuốc theo category_id
    @DeleteMapping("/deleteCategory/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Category has been deleted successfully.");
    }
}
