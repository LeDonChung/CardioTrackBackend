package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.product.model.entities.Medicine;

import java.util.List;

public interface MedicineRepositories extends JpaRepository<Medicine, Long> {
    List<Medicine> findAllByBrandId(Long brandId);

    @Query("SELECT m FROM Medicine m WHERE m.name LIKE %:name%")
    List<Medicine> findMedicineByName(String name);

    @Query("SELECT m FROM Medicine m WHERE m.des LIKE %:des%")
    List<Medicine> findMedicineByDes(String des);

    @Query("SELECT m FROM Medicine m WHERE m.desShort LIKE %:desShort%")
    List<Medicine> findMedicineByDesShort(String desShort);

    List<Medicine> findMedicineByDiscountBetween(int min, int max);

    List<Medicine> findMedicineByInit(String init);
}
