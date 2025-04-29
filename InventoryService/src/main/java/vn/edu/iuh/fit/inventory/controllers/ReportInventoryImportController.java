package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.inventory.services.ReportInventoryImportService;

@RestController
@RequestMapping("/api/v1/report-import")
public class ReportInventoryImportController {
    @Autowired
    private ReportInventoryImportService reportInventoryImportService;

    // Lấy số lượng phiếu nhập kho trong tuần này
    @RequestMapping("/count-this-week")
    public int countInventoryImportThisWeek() {
        return reportInventoryImportService.countInventoryImportThisWeek();
    }

    // Lấy số lượng phiếu nhập kho trong tháng này
    @RequestMapping("/count-this-month")
    public int countInventoryImportThisMonth() {
        return reportInventoryImportService.countInventoryImportThisMonth();
    }

    // Lấy số lượng phiếu nhập kho trong năm này
    @RequestMapping("/count-this-year")
    public int countInventoryImportThisYear() {
        return reportInventoryImportService.countInventoryImportThisYear();
    }
}
