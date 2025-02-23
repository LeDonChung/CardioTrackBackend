package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.product.model.entities.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Lấy tất cả danh mục cha (cấp 1)
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findAllParentCategories();

    // Lấy tất cả danh mục con (cấp 3) của một danh mục cha (danh mục cha đang là cấp 2 - là con của cấp 1)
    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId")
    List<Category> findAllChildCategories(Long parentId);
}
