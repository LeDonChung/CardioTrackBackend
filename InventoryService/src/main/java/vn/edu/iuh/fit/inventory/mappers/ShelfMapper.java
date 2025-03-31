package vn.edu.iuh.fit.inventory.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.inventory.models.dtos.requests.ShelfRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {InventoryMapper.class})
public interface ShelfMapper {
    Shelf toEntity(ShelfRequest request);

    ShelfResponse toDto(Shelf shelf);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Shelf partialUpdate(ShelfRequest shelfRequest, @MappingTarget Shelf shelf);
}
