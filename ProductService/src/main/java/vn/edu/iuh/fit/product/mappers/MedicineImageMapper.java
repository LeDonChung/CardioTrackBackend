package vn.edu.iuh.fit.product.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.models.dtos.requests.BrandRequest;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineImageRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BrandResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineImageResponse;
import vn.edu.iuh.fit.product.models.entities.Brand;
import vn.edu.iuh.fit.product.models.entities.MedicinesImage;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicineImageMapper {
    @Mapping(target = "medicine.id", source = "medicineId")
    MedicinesImage toEntity(MedicineImageRequest request); // Chuyển PostRequest -> Post Entity

    @Mapping(target = "medicineId", source = "medicine.id")
    MedicineImageResponse toResponse(MedicinesImage post); // Chuyển Post Entity -> PostResponse DTO

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MedicinesImage partialUpdate(MedicineImageRequest medicineImageRequest, @MappingTarget MedicinesImage medicinesImage);
}
