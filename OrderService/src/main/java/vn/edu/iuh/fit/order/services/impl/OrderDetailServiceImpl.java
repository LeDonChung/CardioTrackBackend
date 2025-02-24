package vn.edu.iuh.fit.order.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.order.exceptions.OrderException;
import vn.edu.iuh.fit.order.mapper.OrderDetailMapper;
import vn.edu.iuh.fit.order.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderDetailResponse;
import vn.edu.iuh.fit.order.model.entities.Order;
import vn.edu.iuh.fit.order.model.entities.OrderDetail;
import vn.edu.iuh.fit.order.repositories.OrderDetailRepository;
import vn.edu.iuh.fit.order.services.OrderDetailService;
import vn.edu.iuh.fit.order.utils.SystemConstraints;

import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public OrderDetailResponse save(OrderDetailRequest request) {
        OrderDetail orderDetail = null;
        // nếu thêm thì
        if(request.getId() == null) {
            orderDetail = orderDetailMapper.toEntity(request);
            if(request.getOrderId() == null) {
                orderDetail.setOrder(null);
            }
        } else {

        }
        orderDetail = orderDetailRepository.save(orderDetail);
        return orderDetailMapper.toResponse(orderDetail);
    }
}
