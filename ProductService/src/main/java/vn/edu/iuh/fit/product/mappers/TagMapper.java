package vn.edu.iuh.fit.product.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.models.dtos.requests.TagRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.TagResponse;
import vn.edu.iuh.fit.product.models.entities.Tag;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {
    Tag toEntity(TagRequest tagRequest);

    TagResponse toDto(Tag tag);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag partialUpdate(TagRequest tagRequest, @MappingTarget Tag tag);
}