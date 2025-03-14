package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.Supplier;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    //Tìm nhà cung cấp theo tên
    @Query("SELECT s FROM Supplier s WHERE s.name LIKE %:name%")
    Optional<Supplier> getSupplierByName(@Param("name") String name);

}
