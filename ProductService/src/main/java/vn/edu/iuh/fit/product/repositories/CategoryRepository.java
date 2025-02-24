package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.product.model.entities.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Lấy tất cả danh mục cha (cấp 1)
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findAllParentCategories();

    // Lấy tất cả danh mục con Theo parent_id
    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId")
    List<Category> findAllChildCategories(Long parentId);

    // Lấy danh mục thuốc theo title
    @Query("SELECT c FROM Category c WHERE c.title = :title")
    Category findByTitle(String title);
}
