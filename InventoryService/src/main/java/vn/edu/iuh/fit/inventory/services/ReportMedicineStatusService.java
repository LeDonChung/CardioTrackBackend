package vn.edu.iuh.fit.inventory.services;

import java.util.List;

public interface ReportMedicineStatusService {
    // Thống kê số lượng thuốc theo tình trạng (còn hạn, hết hạn và gần hết hạn(nhỏ hơn 6 tháng so với ngày hiện tại))
    List<Object[]> countMedicineStatusByExpirationDate();

}
