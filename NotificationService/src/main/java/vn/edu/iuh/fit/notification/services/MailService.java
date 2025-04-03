package vn.edu.iuh.fit.notification.services;

import jakarta.mail.MessagingException;
import vn.edu.iuh.fit.notification.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.notification.model.dto.request.UserRequest;

public interface MailService {
    void sendMailNotificationOrder(OrderRequest order, String content, String subject) throws MessagingException;
    boolean sendNotificationOrder(OrderRequest order) throws Exception;
    void sendMailNotificationRegisterSuccess(UserRequest user, String content, String subject) throws MessagingException;
    boolean sendNotificationRegisterSuccess(UserRequest user);
}
