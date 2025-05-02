package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.inventory.services.ReportMedicineStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report-medicine-status")
public class ReportMedicineStatusController {
    @Autowired
    private ReportMedicineStatusService reportMedicineStatusService;

    // Thống kê số lượng thuốc theo tình trạng (còn hạn, hết hạn và gần hết hạn(nhỏ hơn 6 tháng so với ngày hiện tại))
     @RequestMapping("/count-medicine-status-by-expiration-date")
     public List<Object[]> countMedicineStatusByExpirationDate() {
         return reportMedicineStatusService.countMedicineStatusByExpirationDate();
     }
}
