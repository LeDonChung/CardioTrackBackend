package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail, Long> {
    // Lấy số lượng sản phẩm theo categoryId
    @Query("SELECT SUM(id.quantity) FROM InventoryDetail id WHERE id.category = :categoryId")
    int getQuantityCategoryProductInventory(Long categoryId);
}
