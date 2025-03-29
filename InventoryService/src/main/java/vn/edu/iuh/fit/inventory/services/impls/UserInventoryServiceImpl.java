package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.mappers.UserInventoryMapper;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserInventoryResponse;
import vn.edu.iuh.fit.inventory.models.entities.UserInventory;
import vn.edu.iuh.fit.inventory.repositories.UserInventoryRepository;
import vn.edu.iuh.fit.inventory.services.UserInventoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInventoryServiceImpl implements UserInventoryService {
    @Autowired
    private UserInventoryRepository userInventoryRepository;

    @Autowired
    private UserInventoryMapper userInventoryMapper;

    @Override
    public List<UserInventoryResponse> getAllUserInventory() {
        List<UserInventory> userInventories = userInventoryRepository.findAll();
        return userInventories.stream().map(u -> userInventoryMapper.toDto(u)).collect(Collectors.toList());
    }
}
