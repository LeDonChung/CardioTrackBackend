package vn.edu.iuh.fit.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;

@FeignClient(name = "notification-service", path = "/api/v1/notification")
public interface NotificationServiceClient {

    @PostMapping("/mail/notification-order")
    public ResponseEntity<Boolean> sendNotificationOrderToEmail(@RequestBody OrderResponse order);

}
