package vn.edu.iuh.fit.pay.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.pay.dto.PaymentRequest;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pay")
public class CheckoutController {
    private final PayOS payOS;

    public CheckoutController(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    @PostMapping("/create-payment-link")
    public ResponseEntity<?> createPaymentLink(@RequestBody PaymentRequest request) {
        try {
            final String productName = request.getProductName();
            final String description = request.getDescription();
            final String returnUrl = request.getReturnUrl();
            final String cancelUrl = request.getCancelUrl();
            final int price = request.getAmount();

            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            ItemData item = ItemData.builder().name(productName).quantity(1).price(price).build();
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .amount(price)
                    .description(description)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .item(item)
                    .build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            return ResponseEntity.ok(Map.of("checkoutUrl", data.getCheckoutUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create payment link"));
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String url = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            url += ":" + serverPort;
        }
        url += contextPath;
        return url;
    }
}