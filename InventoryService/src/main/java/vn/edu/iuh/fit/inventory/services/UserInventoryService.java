package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.models.dtos.responses.UserInventoryResponse;

import java.util.List;

public interface UserInventoryService {
    // Lấy tất cả nhân viên kho
    List<UserInventoryResponse> getAllUserInventory();


}
