package vn.edu.iuh.fit.inventory.services;

import java.util.List;

public interface ReportInventoryImportService {
    List<Object[]> countInventoryImportThisWeek();

    List<Object[]> countInventoryImportThisMonth();

    List<Object[]> countInventoryImportThisYear();

    List<Object[]> countCancelledByWeek();

    List<Object[]> countCancelledByMonth();

    List<Object[]> countCancelledByYear();
}
