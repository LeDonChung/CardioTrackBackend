package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
