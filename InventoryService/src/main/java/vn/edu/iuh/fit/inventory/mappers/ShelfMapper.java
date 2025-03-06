package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShelfMapper {
    Shelf toEntity(Shelf shelf);

    @Mapping(target = "inventory.id", source = "inventory")
    ShelfResponse toDto(Shelf shelf);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Shelf partialUpdate(Shelf shelf);
}
