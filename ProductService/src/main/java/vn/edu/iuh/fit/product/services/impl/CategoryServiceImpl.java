package vn.edu.iuh.fit.product.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.exceptions.CategoryException;
import vn.edu.iuh.fit.product.mappers.CategoryMapper;
import vn.edu.iuh.fit.product.models.dtos.requests.CategoryRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.CategoryProminentResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.CategoryResponse;
import vn.edu.iuh.fit.product.models.entities.Category;
import vn.edu.iuh.fit.product.repositories.CategoryRepository;
import vn.edu.iuh.fit.product.repositories.MedicineRepository;
import vn.edu.iuh.fit.product.services.CategoryService;
import vn.edu.iuh.fit.product.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepositories;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MedicineRepository medicineRepository;

    //Thêm - cập nhật loại sản phẩm
    @Override
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

    //Lấy danh mục theo id
    @Override
    public CategoryResponse getCategoryById(Long id) throws CategoryException {
        Optional<Category> category = categoryRepositories.findById(id);
        if(category.isEmpty()) {
            throw new CategoryException(SystemConstraints.CATEGORY_NOT_FOUND);
        }
        return categoryMapper.toResponse(category.get());
    }

    // Lấy tất cả danh mục cha (cấp 1)
    @Override
    public List<CategoryResponse> getAllParentCategories() {
        List<Category> categories = categoryRepositories.findAllParentCategories();
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

    // Lấy tất cả danh mục con Theo parent_id
    @Override
    public List<CategoryResponse> getAllChildCategories(Long parentID) {
        List<Category> categories = categoryRepositories.findAllChildCategories(parentID);
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

    // Lấy tất cả danh mục con Theo title
    @Override
    public List<CategoryResponse> getCategoryByTitle(String title) {
        List<Category> categories = categoryRepositories.findCategoryByTitle(title);
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

    @Override
    public List<CategoryResponse> getCategoryByLevel(int level) {
        List<Category> categories = categoryRepositories.findCategoryByLevel(level);
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepositories.findAll();
        return categories.stream().map(category -> categoryMapper.toResponse(category)).toList();
    }

    @Override
    public List<CategoryProminentResponse> getProminent() {
        List<Category> categories = categoryRepositories.findAll().get(0).getChildren();
        return categories.stream().map(category -> {
            CategoryProminentResponse categoryProminentResponse = new CategoryProminentResponse();
            categoryProminentResponse.setId(category.getId());
            categoryProminentResponse.setTitle(category.getTitle());
            categoryProminentResponse.setIcon(category.getIcon());
            categoryProminentResponse.setFullPathSlug(category.getFullPathSlug());
            categoryProminentResponse.setLevel(category.getLevel());
            categoryProminentResponse.setNum(medicineRepository.countProductByCategory(category.getId()));
            return categoryProminentResponse;
        }).toList();
    }

}
