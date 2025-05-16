package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.inventory.services.ReportMedicineImportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report-medicine-import")
public class ReportMedicineImportController {
    @Autowired
    private ReportMedicineImportService reportMedicineImportService;

    // Lấy số lượng thuốc nhập theo tuần
    @RequestMapping("/count-week")
    public List<Object[]> countMedicineQuantityByWeek() {
        return reportMedicineImportService.countMedicineQuantityByWeek();
    }

    // Lấy số lượng thuốc nhập theo tháng
    @RequestMapping("/count-month")
    public List<Object[]> countMedicineQuantityByMonth() {
        return reportMedicineImportService.countMedicineQuantityByMonth();
    }

    // Lấy số lượng thuốc nhập theo năm
    @RequestMapping("/count-year")
    public List<Object[]> countMedicineQuantityByYear() {
        return reportMedicineImportService.countMedicineQuantityByYear();
    }

    // Lấy số lượng danh mục theo tuần
    @RequestMapping("/count-category-week")
    public List<Object[]> countCategoryByWeek() {
        return reportMedicineImportService.countCategoryByWeek();
    }
    // Lấy số lượng danh mục theo tháng
    @RequestMapping("/count-category-month")
    public List<Object[]> countCategoryByMonth() {
        return reportMedicineImportService.countCategoryByMonth();
    }

    // Lấy số lượng danh mục theo năm
    @RequestMapping("/count-category-year")
    public List<Object[]> countCategoryByYear() {
        return reportMedicineImportService.countCategoryByYear();
    }
}
