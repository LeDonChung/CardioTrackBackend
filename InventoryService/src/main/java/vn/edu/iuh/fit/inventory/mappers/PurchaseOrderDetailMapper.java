package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderDetailResponse;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrderDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseOrderDetailMapper {
    @Mapping(target = "purchaseOrder.id", source = "purchaseOrderId")
    PurchaseOrderDetail toEntity(PurchaseOrderDetailRequest purchaseOrderDetailRequest);

    @Mapping(target = "purchaseOrderId", source = "purchaseOrder.id")
    PurchaseOrderDetailResponse toDto(PurchaseOrderDetail inventoryImportDetail);
}
