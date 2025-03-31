package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;

@Repository
public interface InventoryImportRepository extends JpaRepository<InventoryImport, Long> {
    // Lấy tất cả đơn nhập theo status = PENDING
    @Query("SELECT po FROM InventoryImport po WHERE po.status = 'PENDING'")
    Page<InventoryImport> getAllPendingImport(Pageable pageable);
}
