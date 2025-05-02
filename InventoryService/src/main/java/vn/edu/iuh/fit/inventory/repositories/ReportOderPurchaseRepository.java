package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;

import java.util.List;

@Repository
public interface ReportOderPurchaseRepository extends JpaRepository<PurchaseOrder, Long> {
    // Thống kê số đơn hàng theo tuần
     @Query(value = """
     SELECT YEAR(order_date) AS year,
            WEEK(order_date, 1) AS week,  -- '1' = tuần bắt đầu từ thứ 2
            COUNT(*) AS total
     FROM purchase_orders
     GROUP BY YEAR(order_date), WEEK(order_date, 1)
     ORDER BY year, week
     """, nativeQuery = true)
     List<Object[]> countAllByWeek();

    // Thống kê số đơn hàng theo tháng
        @Query(value = """
        SELECT YEAR(order_date) AS year,
                MONTH(order_date) AS month,
                COUNT(*) AS total
        FROM purchase_orders
        GROUP BY YEAR(order_date), MONTH(order_date)
        ORDER BY year, month
        """, nativeQuery = true)
        List<Object[]> countAllByMonth();

    // Thống kê số đơn hàng theo năm
        @Query(value = """
        SELECT YEAR(order_date) AS year,
                COUNT(*) AS total
        FROM purchase_orders
        GROUP BY YEAR(order_date)
        ORDER BY year
        """, nativeQuery = true)
        List<Object[]> countAllByYear();

    // Thống kê đơn hàng bị huỷ theo tuần
        @Query(value = """
        SELECT YEAR(order_date) AS year,
                WEEK(order_date, 1) AS week,
                COUNT(*) AS total
        FROM purchase_orders
        WHERE status = 'CANCELED'
        GROUP BY YEAR(order_date), WEEK(order_date, 1)
        ORDER BY year, week
        """, nativeQuery = true)
        List<Object[]> countCancelledByWeek();

    // Thống kê đơn hàng bị hủy theo tháng
        @Query(value = """
        SELECT YEAR(order_date) AS year,
                MONTH(order_date) AS month,
                COUNT(*) AS total
        FROM purchase_orders
        WHERE status = 'CANCELED'
        GROUP BY YEAR(order_date), MONTH(order_date)
        ORDER BY year, month
        """, nativeQuery = true)
        List<Object[]> countCancelledByMonth();

    // Thống kê đơn hàng bị hủy theo năm
        @Query(value = """
        SELECT YEAR(order_date) AS year,
                COUNT(*) AS total
        FROM purchase_orders
        WHERE status = 'CANCELED'
        GROUP BY YEAR(order_date)
        ORDER BY year
        """, nativeQuery = true)
        List<Object[]> countCancelledByYear();

    // Thống kê số lượng thuốc nhập theo nhà cung cấp
    @Query(value = """
    SELECT s.name AS supplier_name,
           SUM(pod.quantity) AS total_quantity
    FROM purchase_orders po
    JOIN purchase_order_details pod ON po.purchase_order_id = pod.purchase_order_id
    JOIN suppliers s ON po.supplier_id = s.supplier_id
    GROUP BY s.name
    ORDER BY total_quantity DESC
    """, nativeQuery = true)
    List<Object[]> countTotalQuantityBySupplier();

    // Thống kê số lượng thuốc nhập bị huỷ theo nhà cung cấp
    @Query(value = """
    SELECT s.name AS supplier_name,
           SUM(pod.quantity) AS total_quantity
    FROM purchase_orders po
    JOIN purchase_order_details pod ON po.purchase_order_id = pod.purchase_order_id
    JOIN suppliers s ON po.supplier_id = s.supplier_id
    WHERE po.status = 'CANCELED'
    GROUP BY s.name
    ORDER BY total_quantity DESC
    """, nativeQuery = true)
    List<Object[]> countCanceledQuantityBySupplier();


}
