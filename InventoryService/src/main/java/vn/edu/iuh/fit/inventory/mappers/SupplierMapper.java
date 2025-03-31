package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.inventory.models.dtos.requests.SupplierRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.SupplierResponse;
import vn.edu.iuh.fit.inventory.models.entities.Supplier;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {
    Supplier toEntity(SupplierRequest request);

    SupplierResponse toDto(Supplier supplier);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Supplier partialUpdate(SupplierRequest supplierRequest, @MappingTarget Supplier supplier);
}
