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
    default InventoryDetail partialUpdate(InventoryDetailRequest inventoryDetailRequest, @MappingTarget InventoryDetail inventoryDetail) {
        if (inventoryDetailRequest.getQuantity() != null) {
            inventoryDetail.setQuantity(inventoryDetail.getQuantity() + inventoryDetailRequest.getQuantity());
        }
        return inventoryDetail;  // ✅ Trả về đối tượng đã cập nhật
    }

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    InventoryDetail updateQuantity(InventoryDetailRequest inventoryDetailRequest, @MappingTarget InventoryDetail inventoryDetail) {
//        inventoryDetail.setQuantity(inventoryDetail.getQuantity() + inventoryDetailRequest.getQuantity());
//        partialUpdate(inventoryDetailRequest, inventoryDetail);
//    }
}
