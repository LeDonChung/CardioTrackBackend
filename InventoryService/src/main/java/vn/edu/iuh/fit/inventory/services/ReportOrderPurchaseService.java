package vn.edu.iuh.fit.inventory.services;

import java.util.List;

public interface ReportOrderPurchaseService {
    // Lấy số lượng phiếu đặt hàng trong tuần
    List<Object[]> countOrderPurchaseByWeek();

    // Lấy số lượng phiếu đặt hàng trong tháng
    List<Object[]> countOrderPurchaseByMonth();

    // Lấy số lượng phiếu đặt hàng trong năm
    List<Object[]> countOrderPurchaseByYear();

    // Lấy số lượng phiếu đặt hàng bị huỷ trong tuần
    List<Object[]> countCancelledByWeek();

    // Lấy số lượng phiếu đặt hàng bị huỷ trong tháng
    List<Object[]> countCancelledByMonth();

    // Lấy số lượng phiếu đặt hàng bị huỷ trong năm này
    List<Object[]> countCancelledByYear();

    // Thống kê số lượng thuốc nhập theo nhà cung cấp
    List<Object[]> countMedicineImportBySupplier();

    // Thống kê số lượng thuốc nhập bị huỷ theo nhà cung cấp
    List<Object[]> countCancelledBySupplier();
}
