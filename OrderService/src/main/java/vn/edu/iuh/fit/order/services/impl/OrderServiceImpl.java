package vn.edu.iuh.fit.order.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.exceptions.OrderException;
import vn.edu.iuh.fit.order.mapper.OrderMapper;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.model.entities.Order;
import vn.edu.iuh.fit.order.model.entities.OrderDetail;
import vn.edu.iuh.fit.order.repositories.OrderDetailRepository;
import vn.edu.iuh.fit.order.repositories.OrderRepository;
import vn.edu.iuh.fit.order.services.OrderService;
import vn.edu.iuh.fit.order.utils.SystemConstraints;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderResponse save(OrderRequest request) throws OrderException {
        Order order = orderMapper.toEntity(request);

        // check if order details is empty
        // check product
        // check customer
        // check quantity
        order = orderRepository.save(order);

        for(OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);
        }

        // send email
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse changeStatus(Long id, OrderStatus status) throws OrderException {
        // Kiem tra xem order co ton tai khong
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            order = orderRepository.save(order);
            return orderMapper.toResponse(order);
        } else {
            throw new OrderException(SystemConstraints.ORDER_NOT_FOUND);
        }

    }
}
