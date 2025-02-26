package vn.edu.iuh.fit.notification.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.notification.services.SMSService;

@RestController
@RequestMapping("/api/v1/notification/sms")
public class SMSController {

    @Autowired
    private SMSService smsService;
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String phoneNumber, @RequestParam String otp) {
        try {
            // Send OTP to the phone number
            String message = smsService.sendOTP(phoneNumber, otp);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("OTP sent failed");
    }
}
