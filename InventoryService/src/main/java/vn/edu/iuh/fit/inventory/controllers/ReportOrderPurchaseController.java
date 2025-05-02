package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.inventory.services.ReportOrderPurchaseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report-order")
public class ReportOrderPurchaseController {

    @Autowired
    private ReportOrderPurchaseService reportOrderPurchaseService;

    // Lấy số lượng đơn hàng đã đặt trong các tuần
    @RequestMapping("/count-week")
    public List<Object[]> countOrderPurchaseByWeek() {
        return reportOrderPurchaseService.countOrderPurchaseByWeek();
    }

    // Lấy số lượng đơn hàng đã đặt trong các tháng
    @RequestMapping("/count-month")
    public List<Object[]> countOrderPurchaseByMonth() {
        return reportOrderPurchaseService.countOrderPurchaseByMonth();
    }

    // Lấy số lượng đơn hàng đã đặt trong các năm
    @RequestMapping("/count-year")
    public List<Object[]> countOrderPurchaseByYear() {
        return reportOrderPurchaseService.countOrderPurchaseByYear();
    }

    // Lấy số lượng đơn hàng đã đặt bị huỷ trong các tuần
    @RequestMapping("/count-cancelled-week")
    public List<Object[]> countCancelledByWeek() {
        return reportOrderPurchaseService.countCancelledByWeek();
    }

    // Lấy số lượng đơn hàng đã đặt bị huỷ trong các tháng
    @RequestMapping("/count-cancelled-month")
    public List<Object[]> countCancelledByMonth() {
        return reportOrderPurchaseService.countCancelledByMonth();
    }

    // Lấy số lượng đơn hàng đã đặt bị huỷ trong các năm
    @RequestMapping("/count-cancelled-year")
    public List<Object[]> countCancelledByYear() {
        return reportOrderPurchaseService.countCancelledByYear();
    }

    // Thống kê số lượng thuốc nhập theo nhà cung cấp
    @RequestMapping("/count-medicine-import-by-supplier")
    public List<Object[]> countMedicineImportBySupplier() {
        return reportOrderPurchaseService.countMedicineImportBySupplier();
    }

    // Thống kê số lượng thuốc nhập bị huỷ theo nhà cung cấp
    @RequestMapping("/count-cancelled-by-supplier")
    public List<Object[]> countCancelledBySupplier() {
        return reportOrderPurchaseService.countCancelledBySupplier();
    }
}
