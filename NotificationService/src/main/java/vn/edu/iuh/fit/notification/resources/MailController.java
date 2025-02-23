package vn.edu.iuh.fit.notification.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.notification.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.notification.services.MailService;

@RestController
@RequestMapping("/api/v1/notification/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/notification-order")
    public ResponseEntity<Boolean> sendNotificationOrderToEmail(@RequestBody OrderRequest order){
        try {
            boolean status = mailService.sendNotificationOrder(order);
            return ResponseEntity.ok(status);
        }catch (Exception e){
            return ResponseEntity.ok(false);
        }
    }
}
