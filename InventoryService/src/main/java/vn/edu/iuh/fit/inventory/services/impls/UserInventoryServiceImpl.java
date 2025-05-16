package vn.edu.iuh.fit.inventory.services.impls;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.clients.UserServiceClient;
import vn.edu.iuh.fit.inventory.enums.UserInventoryRole;
import vn.edu.iuh.fit.inventory.mappers.UserInventoryMapper;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserInventoryResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserResponse;
import vn.edu.iuh.fit.inventory.models.entities.UserInventory;
import vn.edu.iuh.fit.inventory.repositories.UserInventoryRepository;
import vn.edu.iuh.fit.inventory.services.UserInventoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInventoryServiceImpl implements UserInventoryService {
    @Autowired
    private UserInventoryRepository userInventoryRepository;

    @Autowired
    private UserInventoryMapper userInventoryMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public List<UserInventoryResponse> getAllUserInventory() {
        List<UserInventory> userInventories = userInventoryRepository.findAll();
        return userInventories.stream().map(u -> userInventoryMapper.toDto(u)).collect(Collectors.toList());
    }

    //Cập nhật vai trò của nhân viên kho
    @Override
    public UserInventoryResponse updateUserInventoryRole(Long id, UserInventoryRole role) {
       Optional<UserInventory> userInventory = userInventoryRepository.findById(id);
        if (userInventory.isEmpty()) {
            throw new EntityNotFoundException("UserInventory not found");
        }
        UserInventory userInventoryEntity = userInventory.get();
        userInventoryEntity.setRole(role);
        userInventoryRepository.save(userInventoryEntity);

        return userInventoryMapper.toDto(userInventoryEntity);
    }

}
