package vn.edu.iuh.fit.order.mapper;

import org.mapstruct.*;
import vn.edu.iuh.fit.order.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderDetailResponse;
import vn.edu.iuh.fit.order.model.entities.OrderDetail;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDetailMapper {

    OrderDetail toEntity(OrderDetailRequest request);

    @Mapping(target = "orderId", source = "order.id")
    OrderDetailResponse toResponse(OrderDetail post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderDetail partialUpdate(OrderDetailRequest request,@MappingTarget OrderDetail order);
}
