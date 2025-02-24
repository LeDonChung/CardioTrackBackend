package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.product.model.entities.Medicine;

public interface MedicineRepositories extends JpaRepository<Medicine, Long> {
//    Medicine findByName(String name);
}
