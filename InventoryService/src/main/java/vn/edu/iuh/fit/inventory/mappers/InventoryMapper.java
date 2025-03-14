package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryResponse;
import vn.edu.iuh.fit.inventory.models.entities.Inventory;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {
    Inventory toEntity(InventoryRequest request);

    InventoryResponse toDto(Inventory inventory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Inventory partialUpdate(InventoryRequest inventoryRequest, @MappingTarget Inventory inventory);
}
