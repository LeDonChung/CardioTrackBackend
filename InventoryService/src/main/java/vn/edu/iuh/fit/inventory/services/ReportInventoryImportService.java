package vn.edu.iuh.fit.inventory.services;

public interface ReportInventoryImportService {
    int countInventoryImportThisWeek();

    int countInventoryImportThisMonth();

    int countInventoryImportThisYear();
}
