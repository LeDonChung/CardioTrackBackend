package vn.edu.iuh.fit.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.order.model.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
