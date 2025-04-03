package vn.edu.iuh.fit.inventory.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail, Long> {

    //findAllByCategoryId
    @Query("SELECT i FROM InventoryDetail i WHERE i.category = ?1")
    Page<InventoryDetail> findAllByCategoryId(Long categoryId, Pageable pageable);

    //Tổng sản phẩm của kho
    @Query("SELECT SUM(i.quantity) FROM InventoryDetail i")
    Long getTotalQuantity();

    // Tìm chi tiết kho theo medicine và shelfId
    @Query("SELECT i FROM InventoryDetail i WHERE i.medicine = :medicineId AND i.shelf.id = :shelfId")
    InventoryDetail findInventoryDetailByMedicineAndShelf(Long medicineId, Long shelfId);

    // Tìm tổng số lượng của 1 thuốc trong kho (1 thuốc có thể nằm trên nhiều kệ)
    @Query("SELECT SUM(i.quantity) FROM InventoryDetail i WHERE i.medicine = ?1 group by i.medicine")
    Long getTotalQuantityMedicine(Long medicineId);

    // Cập nhật (trừ) số lượng của một thuốc trong kho khi đặt hàng
    @Modifying
    @Transactional
    @Query("UPDATE InventoryDetail i SET i.quantity = i.quantity - ?1 WHERE i.shelf.id = ?2 AND i.medicine = ?3")
    int updateQuantityByShelfAndMedicine(Long quantityToSell, Long shelfId, Long medicineId);

    @Modifying
    @Transactional
    @Query("UPDATE InventoryDetail i SET i.quantity = i.quantity + ?1 WHERE i.shelf.id = ?2 AND i.medicine = ?3 AND (i.quantity + ?1) <= i.shelf.capacity")
    int addQuantityByShelfAndMedicine(Long quantityToAdd, Long shelfId, Long medicineId);

    @Modifying
    @Transactional
    @Query("UPDATE Shelf s SET s.totalProduct = s.totalProduct + ?1 WHERE s.id = ?2")
    int updateTotalProductInShelf(Long quantity, Long shelfId);

    // Lấy danh sách kệ có chỗ trống cho loại thuốc này
    @Query("SELECT i FROM InventoryDetail i WHERE i.medicine = ?1 AND (i.quantity < i.shelf.capacity) ORDER BY (i.shelf.capacity - i.quantity) ASC")
    List<InventoryDetail> getAvailableShelvesForMedicine(Long medicineId);

    // Cập nhật (thêm) số lượng của một thuốc trong kho khi hủy đơn
    @Modifying
    @Transactional
    @Query("UPDATE InventoryDetail i SET i.quantity = i.quantity + ?1 WHERE i.shelf.id = ?2 AND i.medicine = ?3")
    int restoreQuantityByShelfAndMedicine(Long quantityToRestore, Long shelfId, Long medicineId);

    @Query("SELECT i FROM InventoryDetail i WHERE i.medicine = ?1 AND i.quantity > 0 ORDER BY i.quantity ASC")
    Page<InventoryDetail> getInventoryDetailsSortedByQuantity(Long medicineId, Pageable pageable);

    // Gần hết hạn
    @Query("SELECT i FROM InventoryDetail i WHERE i.expirationDate BETWEEN CURRENT_DATE AND :expirationDate")
    Page<InventoryDetail> findMedicinesExpirationDate(Timestamp expirationDate, Pageable pageable);

    // Lâys danh sách thuốc dãd hết hạn
    @Query("SELECT i FROM InventoryDetail i WHERE i.expirationDate < CURRENT_DATE")
    Page<InventoryDetail> findMedicinesExpired(Pageable pageable);

    Page<InventoryDetail> findAllByMedicine(Long medicineId, Pageable pageable);

}

