package vn.edu.iuh.fit.inventory.services;

import java.util.List;

public interface ReportMedicineImportService {
    // Số lượng thuốc nhập theo tuần
    List<Object[]> countMedicineQuantityByWeek();

    // Số lượng thuốc nhập theo tháng
    List<Object[]> countMedicineQuantityByMonth();

    // Số lượng thuốc nhập theo năm
    List<Object[]> countMedicineQuantityByYear();

    // 🔢 Số lượng danh mục theo tuần
    List<Object[]> countCategoryByWeek();

    // 🔢 Số lượng danh mục theo tháng
    List<Object[]> countCategoryByMonth();

    // 🔢 Số lượng danh mục theo năm
    List<Object[]> countCategoryByYear();
}
