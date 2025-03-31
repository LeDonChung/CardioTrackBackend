package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail, Long> {

    //findAllByCategoryId
    @Query("SELECT i FROM InventoryDetail i WHERE i.category = ?1")
    Page<InventoryDetail> findAllByCategoryId(Long categoryId, Pageable pageable);

    //Tổng sản phẩm của kho
    @Query("SELECT SUM(i.quantity) FROM InventoryDetail i")
    Long getTotalQuantity();

//    //Cập nhật (thêm) số lượng của một thuốc khi hủy đơn (thêm lại vào kho)
//    @Modifying
//    @Query("update InventoryDetail s set s.quantity = s.quantity + :quantity where s.medicine = :medicineId")
//    void updateAddTotalProduct(Long medicineId, int quantity);
//
//    //Cập nhật (trừ) số lượng của một thuốc trong kho khi đặt hàng
//    @Modifying
//    @Query("update InventoryDetail s set s.quantity = s.quantity - :quantity where s.medicine = :medicineId")
//    void updateSubtractTotalProduct(Long medicineId, int quantity);

    // Tìm chi tiết kho theo medicine và shelfId
    @Query("SELECT i FROM InventoryDetail i WHERE i.medicine = :medicineId AND i.shelf.id = :shelfId")
    InventoryDetail findInventoryDetailByMedicineAndShelf(Long medicineId, Long shelfId);
}
