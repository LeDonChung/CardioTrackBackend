package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseOrderMapper {
    @Mapping(target = "supplier.name", source = "supplierName")
    @Mapping(target = "supplier.id", source = "supplierId")
    PurchaseOrder toEntity(PurchaseOrderRequest purchaseOrderRequest);

    @Mapping(target = "supplierName", source = "supplier.name")
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierAddress", source = "supplier.address")
    @Mapping(target = "supplierContactInfo", source = "supplier.contactInfo")
    PurchaseOrderResponse toDto(PurchaseOrder purchaseOrder);
}
