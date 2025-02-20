package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.product.model.entities.Category;

public interface CategoryRepositories extends JpaRepository<Category, Long> {
//    Category findByName(String name);
}
