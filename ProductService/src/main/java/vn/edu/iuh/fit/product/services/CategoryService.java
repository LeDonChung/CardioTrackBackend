package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.exceptions.CategoryException;
import vn.edu.iuh.fit.product.models.dtos.requests.CategoryRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.CategoryProminentResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(CategoryRequest request) throws CategoryException;

    //Lấy danh mục theo id
    CategoryResponse getCategoryById(Long id) throws CategoryException;

    // Lấy tất cả danh mục cha (cấp 1)
    List<CategoryResponse> getAllParentCategories();

    // Lấy tất cả danh mục con Theo parent_id
    List<CategoryResponse> getAllChildCategories(Long parentID);

    // Lấy tất cả danh mục con Theo title
    List<CategoryResponse> getCategoryByTitle(String title);

    // Lấy tất cả danh mục
    List<CategoryResponse> getAllCategories();

    List<CategoryProminentResponse> getProminent();
}
