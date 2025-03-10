package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryImportDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportDetailResponse;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImportDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryImportDetailMapper {
    @Mapping(target = "inventoryImport.id", source = "inventoryImportId")
    InventoryImportDetail toEntity(InventoryImportDetailRequest inventoryImportDetailRequest);

    @Mapping(target = "inventoryImportId", source = "inventoryImport.id")
    InventoryImportDetailResponse toDto(InventoryImportDetail inventoryImportDetail);

}
