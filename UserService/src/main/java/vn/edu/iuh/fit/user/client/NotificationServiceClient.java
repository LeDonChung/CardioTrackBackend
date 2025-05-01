package vn.edu.iuh.fit.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;

@FeignClient(name = "notification-service", path = "/api/v1/notification")
public interface NotificationServiceClient {

    @PostMapping("/sms/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String phoneNumber, @RequestParam String otp);
    @PostMapping("/mail/notification-register-success")
    public ResponseEntity<Boolean> sendNotificationRegisterSuccessToEmail(@RequestBody UserRegisterRequest user);

}
