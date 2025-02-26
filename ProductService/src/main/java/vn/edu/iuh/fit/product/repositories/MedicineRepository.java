package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.product.models.entities.Medicine;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findAllByBrandId(Long brandId);

    @Query("SELECT m FROM Medicine m WHERE m.name LIKE %:name%")
    List<Medicine> findMedicineByName(String name);

    @Query("SELECT m FROM Medicine m WHERE m.des LIKE %:des%")
    List<Medicine> findMedicineByDes(String des);

    @Query("SELECT m FROM Medicine m WHERE m.desShort LIKE %:desShort%")
    List<Medicine> findMedicineByDesShort(String desShort);

    List<Medicine> findMedicineByDiscountBetween(int min, int max);

    List<Medicine> findMedicineByInit(String init);

    List<Medicine> findMedicineByPriceBetween(double min, double max);

    List<Medicine> findMedicineByPriceLessThan(double price);

    List<Medicine> findMedicineByPriceGreaterThan(double price);

    List<Medicine> findMedicineByQuantity(int quantity);

    List<Medicine> findMedicineByQuantityBetween(int min, int max);

    List<Medicine> findMedicineByQuantityLessThan(int quantity);

    List<Medicine> findMedicineByQuantityGreaterThan(int quantity);
}
