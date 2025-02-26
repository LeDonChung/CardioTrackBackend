package vn.edu.iuh.fit.order.mapper;

import org.mapstruct.*;
import vn.edu.iuh.fit.order.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderDetailResponse;
import vn.edu.iuh.fit.order.model.entities.OrderDetail;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderDetailMapper {
    OrderDetail toEntity(OrderDetailRequest orderDetailRequest);

    OrderDetailResponse toDto(OrderDetail orderDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderDetail partialUpdate(OrderDetailRequest orderDetailRequest, @MappingTarget OrderDetail orderDetail);
}