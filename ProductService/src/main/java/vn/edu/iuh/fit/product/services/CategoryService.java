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
    public Category addCategory(Category category) {
        return categoryRepositories.save(category);
    }

    //Tìm tất cả loại sản phẩm
    public List<Category> getAllCategories() {
        return categoryRepositories.findAll();
    }

    // Lấy tất cả danh mục cha (cấp 0)
    public List<Category> getAllParentCategories() {
        return categoryRepositories.findAllParentCategories();
    }

    // Lấy tất cả danh mục con của một danh mục cha (danh mục cha đang là cấp 1 - là con của cấp 0)
    public List<Category> getAllChildCategories(Long parentID) {
        return categoryRepositories.findAllChildCategories(parentID);
    }
}
