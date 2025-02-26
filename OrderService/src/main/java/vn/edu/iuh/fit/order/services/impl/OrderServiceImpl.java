package vn.edu.iuh.fit.order.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.order.exceptions.OrderException;
import vn.edu.iuh.fit.order.mapper.OrderMapper;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.model.entities.Order;
import vn.edu.iuh.fit.order.repositories.OrderRepository;
import vn.edu.iuh.fit.order.services.OrderService;
import vn.edu.iuh.fit.order.utils.SystemConstraints;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderResponse save(OrderRequest request) throws OrderException {
        Order order = null;
        // nếu thêm thì
        if(request.getId() == null) {
            order = orderMapper.toEntity(request);
            if(request.getOrderDetailId() == null) {
                order.setOrderDetails(null);
            }
        } else {
            // Tìm order cũ
            Optional<Order> oldOrder = orderRepository.findById(request.getId());
            if (oldOrder.isEmpty()) {
                throw new OrderException(SystemConstraints.ORDER_NOT_FOUND);
            } else {
                order = orderMapper.partialUpdate(request, oldOrder.get());
                if (request.getOrderDetailId() == null) {
                    order.setOrderDetails(null);
                } else {
//                    // Tìm order cha
//                    Optional<Order> parentCategory = orderRepository.findById(request.getOrderDetailId());
//                    if (parentCategory.isEmpty()) {
//                        throw new OrderException(SystemConstraints.ORDER_NOT_FOUND);
//                    } else {
//                        order.setOrderDetails(parentCategory.get());
//                    }
                }
            }
        }
        order = orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public Order changeStatus(Long id, Order order) {
        return null;
    }
}
