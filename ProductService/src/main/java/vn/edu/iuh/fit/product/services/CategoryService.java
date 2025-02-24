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

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepositories;

    @Autowired
    private CategoryMapper categoryMapper;
    //Thêm - cập nhật loại sản phẩm
    public CategoryResponse save(CategoryRequest request) throws CategoryException {
        Category category = null;
        // nếu thêm thì
        if(request.getId() == null) {
            category = categoryMapper.toEntity(request);
            if(request.getParentId() == null) {
                category.setParent(null);
            }
        } else {
            // Tìm category cũ
            Optional<Category> oldCategory = categoryRepositories.findById(request.getId());
            if(oldCategory.isEmpty()) {
                throw new CategoryException(SystemConstraints.CATEGORY_NOT_FOUND);
            } else {
                category = categoryMapper.partialUpdate(request, oldCategory.get());
                if(request.getParentId() == null) {
                    category.setParent(null);
                } else {
                    // Tìm category cha
                    Optional<Category> parentCategory = categoryRepositories.findById(request.getParentId());
                    if(parentCategory.isEmpty()) {
                        throw new CategoryException(SystemConstraints.CATEGORY_NOT_FOUND);
                    } else {
                        category.setParent(parentCategory.get());
                    }
                }
            }
        }

        // nếu sửa thì
        category = categoryRepositories.save(category);
        return categoryMapper.toResponse(category);
    }

    //Tìm tất cả loại sản phẩm
    public List<Category> getAllCategories() {
        return categoryRepositories.findAll();
    }

    //Lấy danh mục theo id
    public CategoryResponse getCategoryById(Long id) throws CategoryException {
        Optional<Category> category = categoryRepositories.findById(id);
        if(category.isEmpty()) {
            throw new CategoryException(SystemConstraints.CATEGORY_NOT_FOUND);
        }
        return categoryMapper.toResponse(category.get());
    }

    // Lấy tất cả danh mục cha (cấp 1)
    public List<CategoryResponse> getAllParentCategories() {
        List<Category> categories = categoryRepositories.findAllParentCategories();
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

    // Lấy tất cả danh mục con Theo parent_id
    public List<CategoryResponse> getAllChildCategories(Long parentID) {
        List<Category> categories = categoryRepositories.findAllChildCategories(parentID);
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

    // Lấy tất cả danh mục con Theo title
    public List<CategoryResponse> getCategoryByTitle(String title) {
        List<Category> categories = categoryRepositories.findCategoryByTitle(title);
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

}
