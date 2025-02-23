package vn.edu.iuh.fit.product.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.product.model.entities.Category;
import vn.edu.iuh.fit.product.services.CategoryService;

@Controller
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

}
