package vn.edu.iuh.fit.pay.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.pay.type.CreatePaymentLinkRequestBody;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("api/v1/pay/order")
public class OrderController {
    private final PayOS payOS;

    public OrderController(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPaymentLink(@RequestBody CreatePaymentLinkRequestBody requestBody) {
        try {
            final String productName = requestBody.getProductName();
            final String description = requestBody.getDescription();
            final String returnUrl = requestBody.getReturnUrl();
            final String cancelUrl = requestBody.getCancelUrl();
            final int price = requestBody.getPrice();

            // Gen order code
            String currentTimeString = String.valueOf(new Date().getTime());
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            ItemData item = ItemData.builder().name(productName).price(price).quantity(1).build();
            PaymentData paymentData = PaymentData.builder()
                    .orderCode(orderCode)
                    .description(description)
                    .amount(price)
                    .item(item)
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            return ResponseEntity.ok(Map.of("checkoutUrl", data.getCheckoutUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to create payment link"));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") long orderId) {
        try {
            PaymentLinkData order = payOS.getPaymentLinkInformation(orderId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") int orderId) {
        try {
            PaymentLinkData order = payOS.cancelPaymentLink(orderId, null);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping(path = "/confirm-webhook")
    public ResponseEntity<?> confirmWebhook(@RequestBody Map<String, String> requestBody) {
        try {
            String webhookUrl = requestBody.getOrDefault("webhookUrl", "");
            if (webhookUrl.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing webhookUrl"));
            }

            String response = payOS.confirmWebhook(webhookUrl);
            return ResponseEntity.ok(Map.of("message", "Webhook confirmed", "data", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
