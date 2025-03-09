package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImportDetail;

import java.util.List;

@Repository
public interface InventoryImportDetailRepository extends JpaRepository<InventoryImportDetail, Long> {
    // Lấy tất cả chi tiết phiếu nhập theo inventoryImportId
    @Query("SELECT iid FROM InventoryImportDetail iid WHERE iid.inventoryImport.id = :inventoryImportId")
    List<InventoryImportDetail> getAllPagesInventoryImportDetail(Long inventoryImportId);
}
