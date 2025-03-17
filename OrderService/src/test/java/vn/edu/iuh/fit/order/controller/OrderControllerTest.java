package vn.edu.iuh.fit.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vn.edu.iuh.fit.order.enums.AddressType;
import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.enums.PaymentMethod;
import vn.edu.iuh.fit.order.exceptions.OrderException;
import vn.edu.iuh.fit.order.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.order.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.services.OrderService;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Bean mock được cung cấp bởi TestConfig (không dùng @MockBean)
    @Autowired
    private OrderService orderService;

    private OrderRequest validOrderRequest;
    private OrderResponse validOrderResponse;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public OrderService orderService() {
            return Mockito.mock(OrderService.class);
        }
    }

    @BeforeEach
    void setup() {
        // Khởi tạo danh sách chi tiết đơn hàng với OrderDetailRequest
        Set<OrderDetailRequest> orderDetails = new HashSet<>();
        orderDetails.add(OrderDetailRequest.builder()
                .id(1L)
                .discount(0)
                .price(100.0)
                .quantity(2)
                .medicine(1L)  // Giả sử medicine có id 1 là hợp lệ
                .orderId(null)
                .build());

        // Tạo đối tượng AddressRequest với cấu trúc mới
        AddressRequest addressRequest = AddressRequest.builder()
                .district("District 1")
                .province("Ho Chi Minh")
                .ward("Ward 1")
                .street("123 Đường ABC")
                .addressType(AddressType.HOME) // Giả sử AddressType có giá trị HOME
                .fullName("Test User")
                .phoneNumber("0123456789")
                .userId(1L)
                .orderId(null)
                .build();

        validOrderRequest = OrderRequest.builder()
                .id(1L)
                .note("Đơn hàng test")
                .exportInvoice(true)
                .status(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.CASH)
                .orderDate(LocalDate.now())
                .feeShip(20.0)
                .customer(1L) // Giả sử khách hàng có id 1 là hợp lệ
                .addressDetail(addressRequest)
                .orderDetails(orderDetails)
                .build();

        validOrderResponse = OrderResponse.builder()
                .id(1L)
                .note("Đơn hàng test")
                .exportInvoice(true)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDate.now())
                .feeShip(20.0)
                .customer(1L)
                .build();
    }

    /**
     * ✅ Kiểm tra tạo đơn hàng thành công
     */
    @Test
    void testCreateOrder_Success() throws Exception {
        Mockito.when(orderService.save(any(OrderRequest.class))).thenReturn(validOrderResponse);

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi người dùng không tồn tại
     */
    @Test
    void testCreateOrder_UserNotFound() throws Exception {
        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Người dùng không tồn tại."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Người dùng không tồn tại."));
    }

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi sản phẩm không hợp lệ
     */
    @Test
    void testCreateOrder_InvalidProduct() throws Exception {
        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Sản phẩm không tồn tại."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Sản phẩm không tồn tại."));
    }

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi không có chi tiết sản phẩm
     */
    @Test
    void testCreateOrder_EmptyOrderDetails() throws Exception {
        // Thiết lập request có orderDetails rỗng
        validOrderRequest.setOrderDetails(new HashSet<>());

        // Cấu hình mock orderService để ném exception với thông báo lỗi cụ thể
        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Chi tiết đơn hàng không được để trống."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Chi tiết đơn hàng không được để trống."));
    }

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi không có địa chỉ giao hàng
     */
    @Test
    void testCreateOrder_InvalidAddress() throws Exception {
        validOrderRequest.setAddressDetail(null);

        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Địa chỉ không hợp lệ."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Địa chỉ không hợp lệ."));
    }
}
