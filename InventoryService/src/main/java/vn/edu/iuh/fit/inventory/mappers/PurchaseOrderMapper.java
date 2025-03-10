package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseOrderMapper {
    @Mapping(target = "supplierName", source = "supplier.name")
    @Mapping(target = "supplierId", source = "supplier.id")
    PurchaseOrderResponse toDto(PurchaseOrder purchaseOrder);
}
