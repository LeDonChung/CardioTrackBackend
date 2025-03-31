package vn.edu.iuh.fit.pay.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.pay.dto.PaymentRequest;
import vn.edu.iuh.fit.pay.dto.ProductRequest;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @Retry(name = "paymentService", fallbackMethod = "createPaymentLinkFallback")
//    @CircuitBreaker(name = "paymentService", fallbackMethod = "createPaymentLinkFallback")
//    @RateLimiter(name = "paymentServiceRateLimiter", fallbackMethod = "createPaymentLinkFallback")
    public ResponseEntity<?> createPaymentLink(@RequestBody PaymentRequest request) throws Exception {
        // Chuẩn bị dữ liệu
        final List<ProductRequest> products = request.getProducts();
        final String description = request.getDescription();
        final String returnUrl = request.getReturnUrl();
        final String cancelUrl = request.getCancelUrl();
        final int price = request.getAmount();
        final Long orderCode = request.getOrderCode();


        System.out.println("------------------------------------");
        System.out.println("Request: " + request);

        List<ItemData> items = new ArrayList<>();
        for (ProductRequest product : products) {
            ItemData item = ItemData.builder()
                    .name(product.getName())
                    .quantity(product.getQuantity())
                    .price(product.getPrice())
                    .build();
            items.add(item);
        }

        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCode)
                .amount(price)
                .description(description)
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .items(items)
                .build();

        System.out.println("Payment Data: " + paymentData);

        // Gọi PayOS để tạo payment link (nếu exception xảy ra, Retry sẽ kích hoạt)
        CheckoutResponseData data = payOS.createPaymentLink(paymentData);

        return ResponseEntity.ok(Map.of("checkoutUrl", data.getCheckoutUrl()));
    }

    public ResponseEntity<?> createPaymentLinkFallback(PaymentRequest request, Throwable throwable) {
        return ResponseEntity.status(500)
                .body(Map.of("error", "Failed to create payment link, please try again later."));
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