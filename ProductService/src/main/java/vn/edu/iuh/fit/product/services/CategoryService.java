package vn.edu.iuh.fit.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.model.entities.Category;
import vn.edu.iuh.fit.product.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepositories;

    //Thêm - cập nhật loại sản phẩm
    public void addCategory(Category category) {
        categoryRepositories.save(category);
    }

    //Tìm tất cả loại sản phẩm
    public List<Category> getAllCategories() {
        return categoryRepositories.findAll();
    }

    //Lấy danh mục theo id
    public Category getCategoryById(Long id) {
        return categoryRepositories.findById(id).orElse(null);
    }

    // Lấy tất cả danh mục cha (cấp 1)
    public List<Category> getAllParentCategories() {
        return categoryRepositories.findAllParentCategories();
    }

    // Lấy tất cả danh mục con Theo parent_id
    public List<Category> getAllChildCategories(Long parentID) {
        return categoryRepositories.findAllChildCategories(parentID);
    }

    // Lấy tất cả danh mục con Theo title
    public Category getCategoryById(String title) {
        return categoryRepositories.findByTitle(title);
    }
}
