package vn.edu.iuh.fit.notification.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.notification.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.notification.model.dto.request.OrderResponse;
import vn.edu.iuh.fit.notification.model.dto.request.UserRequest;
import vn.edu.iuh.fit.notification.services.MailService;

@RestController
@RequestMapping("/api/v1/notification/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/notification-order")
    public ResponseEntity<Boolean> sendNotificationOrderToEmail(@RequestBody OrderResponse order){
        try {
            System.out.println("HIII");
            System.out.println("sendNotificationOrderToEmail: " + order);
            boolean status = mailService.sendNotificationOrder(order);
            return ResponseEntity.ok(status);
        }catch (Exception e){
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/notification-register-success")
    public ResponseEntity<Boolean> sendNotificationRegisterSuccessToEmail(@RequestBody UserRequest user){
        try {
            boolean status = mailService.sendNotificationRegisterSuccess(user);
            return ResponseEntity.ok(status);
        }catch (Exception e){
            return ResponseEntity.ok(false);
        }
    }
}
