package vn.edu.iuh.fit.order.mapper;

import org.mapstruct.*;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.model.entities.Order;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrderDetailMapper.class})
public interface OrderMapper {
    @Mapping(target = "addressId", ignore = true)
    Order toEntity(OrderRequest orderRequest);

    @Mapping(target = "customer", ignore = true)
    OrderResponse toDto(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "addressId", ignore = true)
    Order partialUpdate(OrderRequest orderRequest, @MappingTarget Order order);
}