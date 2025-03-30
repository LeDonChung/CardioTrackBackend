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

@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail, Long> {

    //findAllByCategoryId
    @Query("SELECT i FROM InventoryDetail i WHERE i.category = ?1")
    Page<InventoryDetail> findAllByCategoryId(Long categoryId, Pageable pageable);

    @Query("SELECT SUM(i.quantity) FROM InventoryDetail i")
    Long getTotalQuantity();

    @Query("SELECT SUM(i.quantity) FROM InventoryDetail i WHERE i.medicine = ?1 group by i.medicine")
    Long getTotalQuantityMedicine(Long medicineId);

    @Modifying
    @Transactional
    @Query("UPDATE InventoryDetail i SET i.quantity = i.quantity - ?1 WHERE i.shelf.id = ?2 AND i.medicine = ?3")
    int updateQuantityByShelfAndMedicine(Long quantityToSell, Long shelfId, Long medicineId);

    @Query("SELECT i FROM InventoryDetail i WHERE i.medicine = ?1 AND i.quantity > 0 ORDER BY i.quantity ASC")
    Page<InventoryDetail> getInventoryDetailsSortedByQuantity(Long medicineId, Pageable pageable);

    @Query("SELECT i FROM InventoryDetail i WHERE i.expirationDate BETWEEN CURRENT_DATE AND :expirationDate")
    Page<InventoryDetail> findMedicinesExpirationDate(Timestamp expirationDate, Pageable pageable);

    @Query("SELECT i FROM InventoryDetail i WHERE i.expirationDate < CURRENT_DATE")
    Page<InventoryDetail> findMedicinesExpired(Pageable pageable);

}

