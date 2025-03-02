package vn.edu.iuh.fit.order.services.impl;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.order.client.ProductServiceClient;
import vn.edu.iuh.fit.order.client.UserServiceClient;
import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.exceptions.OrderException;
import vn.edu.iuh.fit.order.mapper.OrderMapper;
import vn.edu.iuh.fit.order.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.model.dto.response.UserResponse;
import vn.edu.iuh.fit.order.model.entities.Order;
import vn.edu.iuh.fit.order.repositories.OrderRepository;
import vn.edu.iuh.fit.order.services.OrderService;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Override
    public OrderResponse save(OrderRequest request) throws OrderException {
        // check if user is valid
        if(request.getCustomer() != null) {
            UserResponse user = userServiceClient.getUserById(request.getCustomer()).getBody().getData();
            if(user == null) {
                throw new OrderException("Người dùng không tồn tại.");
            }
        }
        // check if product is valid
        request.getOrderDetails().forEach(orderDetail -> {
            if(orderDetail.getMedicine() != null) {
                boolean isExists = productServiceClient.isExists(orderDetail.getMedicine()).getBody().getData();
                if(!isExists) {
                    try {
                        throw new OrderException("Sản phẩm không tồn tại.");
                    } catch (OrderException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        // check if address is valid
        Order order = orderMapper.toEntity(request);
        order.setOrderDate(LocalDate.now());
        order.setCustomer(request.getCustomer());
        order.setStatus(OrderStatus.PENDING);

        // Set order id for each order detail

        Order finalOrder = order;

        order.getOrderDetails().forEach(orderDetail -> {
            orderDetail.setOrder(finalOrder);
        });

        order = orderRepository.save(order);

        AddressRequest addressRequest = request.getAddressDetail();
        addressRequest.setOrderId(order.getId());
        AddressResponse addressResponse = userServiceClient.addAddress(addressRequest).getBody().getData();
        order.setAddressId(addressResponse.getId());



        orderRepository.save(order);

        OrderResponse response = orderMapper.toDto(order);
        response.setAddressDetail(addressResponse);
        return response;
    }

    @Override
    public OrderResponse changeStatus(Long id, OrderStatus order) throws OrderException {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isPresent()) {
            Order orderEntity = orderOptional.get();
            orderEntity.setStatus(order);
            orderRepository.save(orderEntity);
            return orderMapper.toDto(orderEntity);
        }
        throw new OrderException("Đơn hàng không tồn tại.");
    }
}
