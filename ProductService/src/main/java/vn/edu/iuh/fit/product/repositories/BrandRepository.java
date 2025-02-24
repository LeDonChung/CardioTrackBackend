package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.product.model.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
