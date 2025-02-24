package vn.edu.iuh.fit.product.mapper;

import org.mapstruct.*;
import vn.edu.iuh.fit.product.model.dto.request.CategoryRequest;
import vn.edu.iuh.fit.product.model.dto.response.CategoryResponse;
import vn.edu.iuh.fit.product.model.entity.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "parent.id", source = "parentId")
    Category toEntity(CategoryRequest request); // Chuyển PostRequest -> Post Entity

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toResponse(Category post); // Chuyển Post Entity -> PostResponse DTO

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryRequest categoryRequest, @MappingTarget Category category);
}