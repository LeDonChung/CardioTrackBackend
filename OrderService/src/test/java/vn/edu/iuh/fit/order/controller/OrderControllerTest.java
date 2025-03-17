package vn.edu.iuh.fit.order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import java.util.Map;
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
        // Khởi tạo danh sách chi tiết đơn hàng hợp lệ
        Set<OrderDetailRequest> orderDetails = new HashSet<>();
        orderDetails.add(OrderDetailRequest.builder()
                .id(1L)
                .discount(0)
                .price(100.0)
                .quantity(2)
                .medicine(1L)  // Giả sử medicine id 1 là hợp lệ
                .orderId(null)
                .build());

        // Tạo đối tượng AddressRequest hợp lệ
        AddressRequest addressRequest = AddressRequest.builder()
                .district("District 1")
                .province("Ho Chi Minh")
                .ward("Ward 1")
                .street("123 Đường ABC")
                .addressType(AddressType.HOME) // Ví dụ: HOME
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
                .customer(1L) // Giả sử khách hàng id 1 hợp lệ
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
        validOrderRequest.setOrderDetails(new HashSet<>());

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

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi thiếu các trường bắt buộc trong OrderRequest
     * (Sử dụng Map để loại bỏ các trường bắt buộc như orderDate, feeShip, paymentMethod, customer)
     */
    @Test
    void testCreateOrder_MissingRequiredFields() throws Exception {
        // Chuyển OrderRequest sang Map và loại bỏ các trường bắt buộc
        Map<String, Object> orderRequestMap = objectMapper.convertValue(validOrderRequest, new TypeReference<Map<String, Object>>() {});
        orderRequestMap.remove("orderDate");
        orderRequestMap.remove("feeShip");
        orderRequestMap.remove("paymentMethod");
        orderRequestMap.remove("customer");

        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Thiếu trường bắt buộc trong đơn hàng."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Thiếu trường bắt buộc trong đơn hàng."));
    }

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi thông tin chi tiết đơn hàng có số liệu không hợp lệ
     */
    @Test
    void testCreateOrder_InvalidOrderDetailNumbers() throws Exception {
        // Thiết lập chi tiết đơn hàng với các giá trị số âm
        OrderDetailRequest invalidDetail = OrderDetailRequest.builder()
                .id(1L)
                .discount(-10)   // discount âm không hợp lệ
                .price(-100.0)   // price âm không hợp lệ
                .quantity(-1)    // quantity âm không hợp lệ
                .medicine(1L)
                .orderId(null)
                .build();
        Set<OrderDetailRequest> invalidOrderDetails = new HashSet<>();
        invalidOrderDetails.add(invalidDetail);
        validOrderRequest.setOrderDetails(invalidOrderDetails);

        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Thông tin chi tiết đơn hàng không hợp lệ."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Thông tin chi tiết đơn hàng không hợp lệ."));
    }

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi định dạng số điện thoại không hợp lệ
     */
    @Test
    void testCreateOrder_InvalidPhoneFormat() throws Exception {
        // Sửa định dạng số điện thoại trong AddressRequest
        AddressRequest invalidAddress = validOrderRequest.getAddressDetail();
        invalidAddress.setPhoneNumber("abc123");
        validOrderRequest.setAddressDetail(invalidAddress);

        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Số điện thoại không hợp lệ."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Số điện thoại không hợp lệ."));
    }

    /**
     * ❌ Kiểm tra tạo đơn hàng thất bại khi các trường enum (status, paymentMethod, addressType) bị null
     */
    @Test
    void testCreateOrder_NullEnumFields() throws Exception {
        validOrderRequest.setStatus(null);
        validOrderRequest.setPaymentMethod(null);
        AddressRequest invalidAddress = validOrderRequest.getAddressDetail();
        invalidAddress.setAddressType(null);
        validOrderRequest.setAddressDetail(invalidAddress);

        Mockito.when(orderService.save(any(OrderRequest.class)))
                .thenThrow(new OrderException("Giá trị enum không được để trống."));

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Giá trị enum không được để trống."));
    }

    /**
     * ✅ Kiểm tra tạo đơn hàng thành công với feeShip bằng 0 (giá trị biên hợp lệ)
     */
    @Test
    void testCreateOrder_ValidBoundary_FeeShipZero() throws Exception {
        validOrderRequest.setFeeShip(0.0);

        OrderResponse responseBoundary = OrderResponse.builder()
                .id(validOrderResponse.getId())
                .note(validOrderResponse.getNote())
                .exportInvoice(true)  // Sử dụng giá trị trực tiếp thay vì gọi getter
                .status(validOrderResponse.getStatus())
                .orderDate(validOrderResponse.getOrderDate())
                .feeShip(0.0)
                .customer(validOrderResponse.getCustomer())
                .build();

        Mockito.when(orderService.save(any(OrderRequest.class))).thenReturn(responseBoundary);

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validOrderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.feeShip").value(0.0));
    }
}