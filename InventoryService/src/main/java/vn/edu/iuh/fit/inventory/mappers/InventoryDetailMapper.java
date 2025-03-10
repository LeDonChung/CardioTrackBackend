package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryDetailResponse;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryDetailMapper {
    @Mapping(target = "inventory.id", source = "inventoryId")
    @Mapping(target = "shelf.id", source = "shelfId")
    InventoryDetail toEntity(InventoryDetailRequest inventoryDetailRequest);

    @Mapping(target = "inventoryId", source = "inventory.id")
    @Mapping(target = "shelfId", source = "shelf.id")
    InventoryDetailResponse toDto(InventoryDetail inventoryrDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryDetail partialUpdate(InventoryDetailRequest inventoryDetailRequest, @MappingTarget InventoryDetail inventoryDetail);
}
