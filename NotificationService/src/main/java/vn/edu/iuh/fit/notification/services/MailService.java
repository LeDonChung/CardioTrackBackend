package vn.edu.iuh.fit.notification.services;

import jakarta.mail.MessagingException;
import vn.edu.iuh.fit.notification.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.notification.model.dto.request.OrderResponse;
import vn.edu.iuh.fit.notification.model.dto.request.UserRequest;

public interface MailService {
    void sendMailNotificationOrder(OrderResponse order, String content, String subject) throws MessagingException;
    boolean sendNotificationOrder(OrderResponse order) throws Exception;
    void sendMailNotificationRegisterSuccess(UserRequest user, String content, String subject) throws MessagingException;
    boolean sendNotificationRegisterSuccess(UserRequest user);
}
