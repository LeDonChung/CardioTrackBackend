package vn.edu.iuh.fit.order.model.dto.response;


import lombok.*;
import vn.edu.iuh.fit.order.enums.OrderStatus;
import vn.edu.iuh.fit.order.model.entities.OrderDetail;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class OrderResponse2 {
    private Long id;
    private String note;
    private boolean exportInvoice;
    private OrderStatus status;
    private LocalDate orderDate;
    private double feeShip;
    private Long customer;
    private AddressResponse addressDetail;
    private List<OrderDetail> orderDetails;

    // Thêm các trường mới để lưu thông tin sản phẩm- tín làm thêm
    private String nameProduct;  // Tên sản phẩm
    private String imageUrl;     // Hình ảnh sản phẩm
    private Long productId;      // ID của sản phẩm

}
