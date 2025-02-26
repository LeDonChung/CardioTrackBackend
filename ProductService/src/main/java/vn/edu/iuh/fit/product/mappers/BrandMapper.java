package vn.edu.iuh.fit.product.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.models.dtos.requests.BrandRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BrandResponse;
import vn.edu.iuh.fit.product.models.entities.Brand;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper {
    Brand toEntity(BrandRequest request); // Chuyển PostRequest -> Post Entity

    BrandResponse toResponse(Brand post); // Chuyển Post Entity -> PostResponse DTO

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Brand partialUpdate(BrandRequest brandRequest, @MappingTarget Brand brand);
}
