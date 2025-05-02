package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.repositories.ReportMedicineImportDetailRepository;
import vn.edu.iuh.fit.inventory.services.ReportMedicineImportService;

import java.util.List;

@Service
public class ReportMedicineImportServiceImpl implements ReportMedicineImportService {

    @Autowired
    private ReportMedicineImportDetailRepository reportMedicineImportDetailRepository;

    @Override
    public List<Object[]> countMedicineQuantityByWeek() {
        return reportMedicineImportDetailRepository.countMedicineQuantityByWeek();
    }

    @Override
    public List<Object[]> countMedicineQuantityByMonth() {
        return reportMedicineImportDetailRepository.countMedicineQuantityByMonth();
    }

    @Override
    public List<Object[]> countMedicineQuantityByYear() {
        return reportMedicineImportDetailRepository.countMedicineQuantityByYear();
    }
}
