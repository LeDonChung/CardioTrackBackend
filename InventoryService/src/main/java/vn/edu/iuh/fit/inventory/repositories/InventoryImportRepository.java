package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;

@Repository
public interface InventoryImportRepository extends JpaRepository<InventoryImport, Long> {
}
