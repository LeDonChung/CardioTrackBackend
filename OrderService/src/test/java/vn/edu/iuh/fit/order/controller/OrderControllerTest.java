package vn.edu.iuh.fit.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.jwt.JwtConfig;
import vn.edu.iuh.fit.order.jwt.JwtService;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.services.OrderService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({OrderControllerTest.OrderControllerTestConfig.class, OrderControllerTest.TestConfig.class})
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JwtConfig jwtConfig() {
            return new JwtConfig(/* truyền các tham số cần thiết nếu có */);
        }
    }

    @TestConfiguration
    static class JwtTestConfig {
        @Bean
        public JwtService jwtService() {
            return Mockito.mock(JwtService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    // Bean giả được cung cấp từ cấu hình test bên dưới
    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class OrderControllerTestConfig {
        @Bean
        public OrderService orderService() {
            return Mockito.mock(OrderService.class);
        }
    }

    @Test
    public void testAddOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest();

        OrderResponse orderResponse = new OrderResponse();

        Mockito.when(orderService.save(Mockito.any(OrderRequest.class)))
                .thenReturn(orderResponse);

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testChangeStatus() throws Exception {
        Long orderId = 1L;
        OrderStatus status = OrderStatus.PAID;
        OrderResponse orderResponse = new OrderResponse();

        Mockito.when(orderService.changeStatus(Mockito.eq(orderId), Mockito.eq(status)))
                .thenReturn(orderResponse);

        mockMvc.perform(put("/api/v1/order/change-status/{id}", orderId)
                        .param("status", status.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testGetOrdersByUserId() throws Exception {
        Long userId = 1L;
        OrderResponse orderResponse = new OrderResponse();
        List<OrderResponse> orders = Arrays.asList(orderResponse);

        Mockito.when(orderService.getOrdersByUserId(Mockito.eq(userId)))
                .thenReturn(orders);

        String token = "Bearer some-valid-token";

        mockMvc.perform(get("/api/v1/order/user/{id}", userId)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testRecommend() throws Exception {
        OrderResponse orderResponse = new OrderResponse();
        List<OrderResponse> orders = Arrays.asList(orderResponse);

        Mockito.when(orderService.recommend()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/order/recommend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.success").value(true));
    }
}
