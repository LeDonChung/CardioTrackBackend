package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImportDetail;

import java.util.List;

@Repository
public interface ReportMedicineImportDetailRepository extends JpaRepository<InventoryImportDetail, Long> {

    // Số lượng thuốc nhập theo tuần
    @Query(value = """
        SELECT YEAR(ii.import_date) AS year,
               WEEK(ii.import_date, 1) AS week,
               SUM(iid.quantity) AS total_quantity
        FROM inventory_import_details iid
        JOIN inventory_imports ii ON iid.import_id = ii.import_id
        GROUP BY YEAR(ii.import_date), WEEK(ii.import_date, 1)
        ORDER BY year, week
    """, nativeQuery = true)
    List<Object[]> countMedicineQuantityByWeek();

    // Số lượng thuốc nhập theo tháng
    @Query(value = """
        SELECT YEAR(ii.import_date) AS year,
               MONTH(ii.import_date) AS month,
               SUM(iid.quantity) AS total_quantity
        FROM inventory_import_details iid
        JOIN inventory_imports ii ON iid.import_id = ii.import_id
        GROUP BY YEAR(ii.import_date), MONTH(ii.import_date)
        ORDER BY year, month
    """, nativeQuery = true)
    List<Object[]> countMedicineQuantityByMonth();

    // Số lượng thuốc nhập theo năm
    @Query(value = """
        SELECT YEAR(ii.import_date) AS year,
               SUM(iid.quantity) AS total_quantity
        FROM inventory_import_details iid
        JOIN inventory_imports ii ON iid.import_id = ii.import_id
        GROUP BY YEAR(ii.import_date)
        ORDER BY year
    """, nativeQuery = true)
    List<Object[]> countMedicineQuantityByYear();
}
