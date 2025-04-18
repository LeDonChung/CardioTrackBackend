package vn.edu.iuh.fit.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.order.model.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(Long customerId);
}
