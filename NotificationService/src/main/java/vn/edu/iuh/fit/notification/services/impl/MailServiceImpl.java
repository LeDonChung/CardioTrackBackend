package vn.edu.iuh.fit.notification.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.notification.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.notification.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.notification.model.dto.request.UserRequest;
import vn.edu.iuh.fit.notification.services.MailService;
import vn.edu.iuh.fit.notification.services.ThymeleafService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private ThymeleafService thymeleafService;

    private double calculatorTotalPrice(OrderRequest order) {
        double totalPrice = 0;

        for (OrderDetailRequest orderDetail : order.getOrderDetails()) {
            double price = orderDetail.getPrice() * orderDetail.getQuantity();
            totalPrice += price - (price * (orderDetail.getDiscount()/100));
        }
        return totalPrice;
    }

    @Override
    public void sendMailNotificationOrder(OrderRequest order, String content, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        String to = order.getCustomer().getEmail();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setTo(to);
        helper.setSubject(subject);
        // content
        Map<String, Object> variables = new HashMap<>();
        variables.put("content", content);

        helper.setText(thymeleafService.createContent("init-mail.html", variables), true);
        helper.setFrom(email);
        mailSender.send(message);
    }

    @Override
    public boolean sendNotificationOrder(OrderRequest order) {
        if(order == null){
            return false;
        }
        String subject = "Xác nhận đơn hàng nhà thuốc Thera Care";
        String ad = String.format("%s, %s, %s, %s", order.getAddressDetail().getAddress().getStreet(),
                order.getAddressDetail().getAddress().getWard(), order.getAddressDetail().getAddress().getDistrict(),
                order.getAddressDetail().getAddress().getProvince());
        StringBuilder contentBuilder = new StringBuilder()
                .append(String.format("<p>Kính gửi %s,</p>", order.getCustomer().getFullName()))
                .append("<p>Cảm ơn bạn đã đặt hàng tại nhà thuốc của chúng tôi. Đơn hàng của bạn đã được xác nhận và chúng tôi sẽ giao hàng trong vòng 1-3 ngày.</p>")
                .append("<p><strong>Thông tin đơn hàng:</strong></p>")
                .append(String.format("<p><strong>Ngày đặt hàng:</strong> %s</p>", order.getOrderDate()))
                .append(String.format("<p><strong>Tổng tiền:</strong> %,.2f VND</p>", calculatorTotalPrice(order)))
                .append("<p><strong>Thông tin giao hàng:</strong></p>")
                .append(String.format("<p><strong>Người nhận:</strong> %s</p>", order.getAddressDetail().getFullName()))
                .append(String.format("<p><strong>Số điện thoại:</strong> <a href='tel:%s'>%s</a></p>", order.getAddressDetail().getPhoneNumber(), order.getAddressDetail().getPhoneNumber()))
                .append(String.format("<p><strong>Địa chỉ:</strong> %s (%s)</p>", ad, order.getAddressDetail().getAddressType()))
                .append("<p><strong>Danh sách sản phẩm:</strong></p>")
                .append("<table border='1' cellspacing='0' cellpadding='5'>")
                .append("<tr><th>STT</th><th>Tên mặt hàng</th><th>Số lượng</th><th>Đơn giá</th><th>Giảm giá</th><th>Thành tiền</th></tr>");

        int index = 1;
        for (OrderDetailRequest item : order.getOrderDetails()) {
            contentBuilder.append(String.format(
                    "<tr><td>%d</td><td>%s</td><td>%d</td><td>%,.2f VND</td><td>%,.2f VND</td><td>%,.2f VND</td></tr>",
                    index++, item.getMedicine().getName(), item.getQuantity(), item.getPrice(), item.getDiscount(), item.getPrice() * item.getQuantity() - (item.getPrice() * item.getQuantity() * (item.getDiscount()/100))));
        }

        contentBuilder.append("</table>")
                .append("<p>Chúng tôi sẽ liên hệ với bạn khi đơn hàng được giao. Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.</p>")
                .append("<p>Nhà thuốc Thera Care xin cảm ơn bạn đã tin tưởng nhà thuốc và mua sắm tại nhà thuốc của chúng tôi!</p>");
        try {
            sendMailNotificationOrder(order, contentBuilder.toString(), subject);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void sendMailNotificationRegisterSuccess(UserRequest user, String content, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        String to = user.getEmail();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        helper.setTo(to);
        helper.setSubject(subject);
        // content
        Map<String, Object> variables = new HashMap<>();
        variables.put("content", content);

        helper.setText(thymeleafService.createContent("init-mail.html", variables), true);
        helper.setFrom(email);
        mailSender.send(message);
    }

    @Override
    public boolean sendNotificationRegisterSuccess(UserRequest user) {
        String subject = "Thông báo đăng ký tài khoản nhà thuốc Thera Care thành công!";
        StringBuilder contentBuilder = new StringBuilder()
                .append(String.format("<p>Kính gửi %s,</p>", user.getFullName()))
                .append(String.format("<p>Số điện thoại đã đăng ký: %s.</p>", user.getPhoneNumber()))
                .append("<p>Cảm ơn bạn đã đăng ký tài khoản nhà thuốc Thera Care. Tài khoản của bạn đã được kích hoạt và bạn có thể sử dụng dịch vụ của nhà thuốc.</p>")
                .append("<p>Chúng tôi rất vui mừng khi bạn đã trở thành một phần của cộng đồng nhà thuốc Thera Care. Chúng tôi hy vọng bạn sẽ có những trải nghiệm tuyệt vời khi sử dụng dịch vụ của nhà thuốc.</p>")
                .append("<p>Chúc bạn một ngày tốt lành!</p>");
        try {
            sendMailNotificationRegisterSuccess(user, contentBuilder.toString(), subject);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
