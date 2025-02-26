package vn.edu.iuh.fit.product.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.models.dtos.requests.ImageRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.ImageResponse;
import vn.edu.iuh.fit.product.models.entities.Image;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {
    Image toEntity(ImageRequest request);

    ImageResponse toDto(Image image);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Image partialUpdate(ImageResponse imageResponse, @MappingTarget Image image);
}