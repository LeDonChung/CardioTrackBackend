package vn.edu.iuh.fit.pay.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import java.util.Map;

@RestController
@RequestMapping("api/v1/pay")
public class PaymentController {
  private final PayOS payOS;

  public PaymentController(PayOS payOS) {
    this.payOS = payOS;
  }

  @PostMapping("/webhook")
  public ResponseEntity<?> handleWebhook(@RequestBody Webhook webhook) {
    try {
      WebhookData data = payOS.verifyPaymentWebhookData(webhook);
      System.out.println("Webhook received: " + data);

//      if ("PAID".equals(data.getStatus())) {
//        System.out.println("Thanh toán thành công: " + data.getTransactionId());
//        // TODO: Gọi repository hoặc service cập nhật trạng thái đơn hàng trong database
//        // Ví dụ: orderService.updateOrderStatus(data.getTransactionId(), "PAID");
//      } else {
//        System.out.println("Giao dịch chưa hoàn tất hoặc thất bại.");
//      }

      return ResponseEntity.ok(Map.of("message", "Webhook received"));
    } catch (Exception e) {
      return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    }
  }
}
