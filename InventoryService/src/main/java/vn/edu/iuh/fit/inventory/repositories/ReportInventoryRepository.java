package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;

import java.util.List;

@Repository
public interface ReportInventoryRepository extends JpaRepository<InventoryImport, Long> {
    //Thống kê số đơn nhập theo tuần
    @Query(value = """
    SELECT YEAR(import_date) AS year,
           WEEK(import_date, 1) AS week,  -- '1' = tuần bắt đầu từ thứ 2
           COUNT(*) AS total
    FROM inventory_imports
    GROUP BY YEAR(import_date), WEEK(import_date, 1)
    ORDER BY year, week
""", nativeQuery = true)
    List<Object[]> countAllByWeek();


    //Thống kê số đơn nhập theo tháng
    @Query(value = """
    SELECT YEAR(import_date) AS year,
           MONTH(import_date) AS month,
           COUNT(*) AS total
    FROM inventory_imports
    GROUP BY YEAR(import_date), MONTH(import_date)
    ORDER BY year, month
""", nativeQuery = true)
    List<Object[]> countAllByMonth();


    //Thống kê số đơn nhập theo năm
    @Query(value = """
    SELECT YEAR(import_date) AS year,
           COUNT(*) AS total
    FROM inventory_imports
    GROUP BY YEAR(import_date)
    ORDER BY year
""", nativeQuery = true)
    List<Object[]> countAllByYear();

    //Thống kê đơn nhập bị huỷ theo tuần
    @Query(value = """
    SELECT YEAR(import_date) AS year,
           WEEK(import_date, 1) AS week,
           COUNT(*) AS total
    FROM inventory_imports
    WHERE status = 'CANCELLED'
    GROUP BY YEAR(import_date), WEEK(import_date, 1)
    ORDER BY year, week
""", nativeQuery = true)
    List<Object[]> countCancelledByWeek();

    //Thống kê đơn nhập bị hủy theo tháng
    @Query(value = """
    SELECT YEAR(import_date) AS year,
           MONTH(import_date) AS month,
           COUNT(*) AS total
    FROM inventory_imports
    WHERE status = 'CANCELLED'
    GROUP BY YEAR(import_date), MONTH(import_date)
    ORDER BY year, month
""", nativeQuery = true)
    List<Object[]> countCancelledByMonth();

    //Thống kê đơn nhập bị hủy theo năm
    @Query(value = """
    SELECT YEAR(import_date) AS year,
           COUNT(*) AS total
    FROM inventory_imports
    WHERE status = 'CANCELLED'
    GROUP BY YEAR(import_date)
    ORDER BY year
""", nativeQuery = true)
    List<Object[]> countCancelledByYear();

}
