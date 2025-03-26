package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserInventoryResponse;

public interface UserInventoryService {
    // Lấy tất cả nhân viên kho
    PageDTO<UserInventoryResponse> getAllUserInventory(int page, int size, String sortBy, String sortName);


}
