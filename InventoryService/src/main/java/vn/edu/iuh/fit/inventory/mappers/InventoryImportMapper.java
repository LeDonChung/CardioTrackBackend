package vn.edu.iuh.fit.inventory.mappers;


import org.mapstruct.*;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryImportRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportResponse;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {InventoryDetailMapper.class})
public interface InventoryImportMapper {
    @Mapping(target = "inventory.id", source = "inventory")
    @Mapping(target = "supplier.id", source = "supplier")
    InventoryImport toEntity(InventoryImportRequest inventoryImportRequest);

    @Mapping(target = "inventory", source = "inventory.id")
    @Mapping(target = "supplier", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    InventoryImportResponse toDto(InventoryImport inventoryImport);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    InventoryImport partialUpdate(InventoryImportRequest inventoryImportRequest, @MappingTarget InventoryImport inventoryImport);
}
