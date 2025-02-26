package vn.edu.iuh.fit.product.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineReponse;
import vn.edu.iuh.fit.product.models.entities.Medicine;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicineMapper {
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "brand.id", source = "brandId")
    Medicine toEntity(MedicineRequest request); // Chuyển PostRequest -> Post Entity

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "brandId", source = "brand.id")
    MedicineReponse toResponse(Medicine post); // Chuyển Post Entity -> PostResponse DTO

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medicine partialUpdate(MedicineRequest medicineRequest, @MappingTarget Medicine medicine);
}
