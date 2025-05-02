package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;

import java.util.List;

public interface ReportMedicineStatusRepository extends JpaRepository<InventoryDetail, Long> {
    // Thống kê số lượng thuốc theo tình trạng (còn hạn, hết hạn và gần hết hạn(nhỏ hơn 6 tháng so với ngày hiện tại))
    @Query(value = """
    SELECT 
        CASE
            WHEN expiration_date < CURRENT_DATE THEN 'Hết hạn'
            WHEN expiration_date BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 6 MONTH) THEN 'Gần hết hạn'
            ELSE 'Còn hạn'
        END AS status,
        SUM(quantity) AS total
    FROM inventory_details
    GROUP BY status
    """, nativeQuery = true)
    List<Object[]> countMedicineStatusByExpirationDate();

}

