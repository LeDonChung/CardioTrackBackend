package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrderDetail;

import java.util.List;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Long> {
    // Lấy tất cả chi tiết phiếu nhập theo purchaseOrderId
    @Query("SELECT pod FROM PurchaseOrderDetail pod WHERE pod.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderDetail> getAllPurchaseOrderDetailByPurchaseOrderId(Long purchaseOrderId);
}
