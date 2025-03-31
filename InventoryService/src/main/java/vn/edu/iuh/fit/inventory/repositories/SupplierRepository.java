package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.Supplier;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    //Tìm nhiều nhà cung cấp theo tên gần đúng
    @Query("SELECT s FROM Supplier s WHERE s.name LIKE %:name%")
    List<Supplier> getSupplierByNameLike(@Param("name") String name);

}
