package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.repositories.ReportInventoryRepository;
import vn.edu.iuh.fit.inventory.services.ReportInventoryImportService;

import java.util.List;

@Service
public class ReportInventoryImportServiceImpl implements ReportInventoryImportService {
    @Autowired
    private ReportInventoryRepository reportImportRepository;
    @Override
    public List<Object[]> countInventoryImportThisWeek() {
        return reportImportRepository.countAllByWeek();
    }

    @Override
    public List<Object[]> countInventoryImportThisMonth() {
        return reportImportRepository.countAllByMonth();
    }

    @Override
    public List<Object[]> countInventoryImportThisYear() {
        return reportImportRepository.countAllByYear();
    }

    @Override
    public List<Object[]> countCancelledByWeek() {
        return reportImportRepository.countCancelledByWeek();
    }

    @Override
    public List<Object[]> countCancelledByMonth() {
        return reportImportRepository.countCancelledByMonth();
    }

    @Override
    public List<Object[]> countCancelledByYear() {
        return reportImportRepository.countCancelledByYear();
    }
}
