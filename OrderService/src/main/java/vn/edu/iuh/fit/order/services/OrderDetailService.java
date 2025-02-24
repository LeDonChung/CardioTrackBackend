package vn.edu.iuh.fit.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.order.model.entities.OrdersDetail;
import vn.edu.iuh.fit.order.repositories.OrderDetailRepository;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Thêm OrderDetail
    public OrdersDetail addOrderDetail(OrdersDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
