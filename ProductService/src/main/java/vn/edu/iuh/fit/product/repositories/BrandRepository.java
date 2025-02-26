package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.product.models.entities.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
