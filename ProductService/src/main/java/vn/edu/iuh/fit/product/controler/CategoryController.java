package vn.edu.iuh.fit.product.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.model.entities.Category;
import vn.edu.iuh.fit.product.services.CategoryService;

import java.util.Map;

@Controller
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

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

    @GetMapping("/home")
    public String home() {
        return "Category Service";
    }


}
