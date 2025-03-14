package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.exceptions.SupplierException;
import vn.edu.iuh.fit.inventory.models.dtos.requests.SupplierRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.SupplierResponse;

import java.util.List;

public interface SupplierService {
    //Thêm mới nhà cung cấp
    SupplierResponse save(SupplierRequest request) throws SupplierException;

    //Tìm danh sách nhà cung cấp
    List<SupplierResponse> getAllSuppliers();

    //Tìm nhà cung cấp theo id
    SupplierResponse getSupplierById(Long id) throws SupplierException;

    //Tìm nhà cung cấp theo tên
    SupplierResponse getSupplierByName(String name) throws SupplierException;

    //Cập nhật nhà cung cấp
    //Xóa nhà cung cấp
}
