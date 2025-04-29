package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.repositories.ReportInventoryRepository;
import vn.edu.iuh.fit.inventory.repositories.ReportOderPurchaseRepository;
import vn.edu.iuh.fit.inventory.services.ReportOrderPurchaseService;

import java.util.List;

@Service
public class ReportOrderPurchaseServiceImpl implements ReportOrderPurchaseService {
    @Autowired
    private ReportOderPurchaseRepository reportPurchaseRepository;

    // Thống kê số lượng đơn hàng đã đặt trong tuần
    @Override
    public List<Object[]> countOrderPurchaseByWeek() {
        return reportPurchaseRepository.countAllByWeek();
    }

    // Thống kê số lượng đơn hàng đã đặt trong tháng
    @Override
    public List<Object[]> countOrderPurchaseByMonth() {
        return reportPurchaseRepository.countAllByMonth();
    }

    // Thống kê số lượng đơn hàng đã đặt trong năm
    @Override
    public List<Object[]> countOrderPurchaseByYear() {
        return reportPurchaseRepository.countAllByYear();
    }

    // Thống kê số lượng đơn hàng đã đặt bị huỷ trong tuần
    @Override
    public List<Object[]> countCancelledByWeek() {
        return reportPurchaseRepository.countCancelledByWeek();
    }

    // Thống kê số lượng đơn hàng đã đặt bị huỷ trong tháng
    @Override
    public List<Object[]> countCancelledByMonth() {
        return reportPurchaseRepository.countCancelledByMonth();
    }

    // Thống kê số lượng đơn hàng đã đặt bị huỷ trong năm
    @Override
    public List<Object[]> countCancelledByYear() {
        return reportPurchaseRepository.countCancelledByYear();
    }

}
