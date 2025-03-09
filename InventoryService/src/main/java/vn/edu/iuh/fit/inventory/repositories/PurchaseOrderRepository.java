package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    // Lấy tất cả phiếu mua hàng theo status = PENDING
    @Query("SELECT po FROM PurchaseOrder po WHERE po.status = 'PENDING'")
    List<PurchaseOrder> getAllPendingPurchaseOrder();

}
