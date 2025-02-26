package vn.edu.iuh.fit.order.services;

import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.exceptions.OrderException;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.model.entities.Order;

public interface OrderService {

    //Thêm Order
    OrderResponse save(OrderRequest request) throws OrderException;

    //Thay đổi trạng thái Order
    OrderResponse changeStatus(Long id, OrderStatus order) throws OrderException;
}
