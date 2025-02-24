package vn.edu.iuh.fit.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.exceptions.CategoryException;
import vn.edu.iuh.fit.product.mapper.CategoryMapper;
import vn.edu.iuh.fit.product.model.dto.request.CategoryRequest;
import vn.edu.iuh.fit.product.model.dto.response.CategoryResponse;
import vn.edu.iuh.fit.product.model.entity.Category;
import vn.edu.iuh.fit.product.repositories.CategoryRepository;
import vn.edu.iuh.fit.product.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    public CategoryResponse save(CategoryRequest request) throws CategoryException;

    //Lấy danh mục theo id
    public CategoryResponse getCategoryById(Long id) throws CategoryException;

    // Lấy tất cả danh mục cha (cấp 1)
    public List<CategoryResponse> getAllParentCategories();
    // Lấy tất cả danh mục con Theo parent_id
    public List<CategoryResponse> getAllChildCategories(Long parentID);

    // Lấy tất cả danh mục con Theo title
    public List<CategoryResponse> getCategoryByTitle(String title);

}
