package vn.edu.iuh.fit.product.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.product.models.entities.Image;
import vn.edu.iuh.fit.product.models.entities.Medicine;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {BrandMapper.class, TagMapper.class, SpecificationMapper.class, CategoryMapper.class, Image.class})
public interface MedicineMapper {
    Medicine toEntity(MedicineRequest request);

    MedicineResponse toDto(Medicine medicine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medicine partialUpdate(MedicineRequest medicineRequest, @MappingTarget Medicine medicine);
}