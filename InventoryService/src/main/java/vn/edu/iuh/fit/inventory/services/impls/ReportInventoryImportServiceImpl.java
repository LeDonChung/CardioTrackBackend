package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.repositories.ReportRepository;
import vn.edu.iuh.fit.inventory.services.ReportInventoryImportService;

@Service
public class ReportInventoryImportServiceImpl implements ReportInventoryImportService {
    @Autowired
    private ReportRepository reportImportRepository;
    @Override
    public int countInventoryImportThisWeek() {
        return reportImportRepository.countInventoryImportThisWeek();
    }

    @Override
    public int countInventoryImportThisMonth() {
        return reportImportRepository.countInventoryImportThisMonth();
    }

    @Override
    public int countInventoryImportThisYear() {
        return reportImportRepository.countInventoryImportThisYear();
    }
}
