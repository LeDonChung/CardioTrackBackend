package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.repositories.ReportMedicineStatusRepository;
import vn.edu.iuh.fit.inventory.services.ReportMedicineStatusService;

import java.util.List;

@Service
public class ReportMedicineStatusServiceImpl implements ReportMedicineStatusService {
    @Autowired
    private ReportMedicineStatusRepository reportMedicineStatusRepository;

    // Thống kê số lượng thuốc theo tình trạng (còn hạn, hết hạn và gần hết hạn(nhỏ hơn 6 tháng so với ngày hiện tại))
    @Override
    public List<Object[]> countMedicineStatusByExpirationDate() {
        return reportMedicineStatusRepository.countMedicineStatusByExpirationDate();
    }
}
