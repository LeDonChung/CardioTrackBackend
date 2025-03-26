package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.Mapping;
import vn.edu.iuh.fit.inventory.models.dtos.requests.UserInventoryRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.UserInventoryResponse;
import vn.edu.iuh.fit.inventory.models.entities.UserInventory;;

public interface UserInventoryMapper {
    @Mapping(target = "inventory.id", source = "inventory")
    UserInventory toEntity(UserInventoryRequest userInventoryRequest);

    @Mapping(target = "inventory", source = "inventory.id")
    UserInventoryResponse toDto(UserInventory userInventory);
}
