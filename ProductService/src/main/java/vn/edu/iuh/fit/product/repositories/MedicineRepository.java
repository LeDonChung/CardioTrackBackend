package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.product.models.entities.Medicine;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
