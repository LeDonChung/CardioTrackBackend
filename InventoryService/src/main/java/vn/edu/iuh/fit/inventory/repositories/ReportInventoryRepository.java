package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;

@Repository
public interface ReportInventoryRepository extends JpaRepository<InventoryImport, Long> {
    //Thống kê lượng hàng nhập theo tuần
    @Query(value = "SELECT COUNT(*) FROM inventory_imports WHERE WEEK(import_date) = WEEK(CURRENT_DATE)", nativeQuery = true)
    int countInventoryImportThisWeek();

    //Thống kê lượng hàng nhập theo tháng
    @Query(value = "SELECT COUNT(*) FROM inventory_imports WHERE MONTH(import_date) = MONTH(CURRENT_DATE)", nativeQuery = true)
    int countInventoryImportThisMonth();

    //Thống kê lượng hàng nhập theo năm
    @Query(value = "SELECT COUNT(*) FROM inventory_imports WHERE YEAR(import_date) = YEAR(CURRENT_DATE)", nativeQuery = true)
    int countInventoryImportThisYear();
}
