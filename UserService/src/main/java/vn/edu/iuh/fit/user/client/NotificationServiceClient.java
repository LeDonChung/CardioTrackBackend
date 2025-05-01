package vn.edu.iuh.fit.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", path = "/api/v1/notification")
public interface NotificationServiceClient {

    @PostMapping("/sms/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String phoneNumber, @RequestParam String otp);

}
