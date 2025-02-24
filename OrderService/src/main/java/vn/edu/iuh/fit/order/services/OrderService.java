package vn.edu.iuh.fit.order.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.order.model.entities.Order;
import vn.edu.iuh.fit.order.repositories.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    //Thêm Order
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }
}
