package vn.edu.iuh.fit.product.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.models.dtos.requests.SpecificationRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.SpecificationResponse;
import vn.edu.iuh.fit.product.models.entities.Specification;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecificationMapper {
    Specification toEntity(SpecificationRequest specificationRequest);

    SpecificationResponse toDto(Specification specification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Specification partialUpdate(SpecificationRequest specificationRequest, @MappingTarget Specification specification);
}