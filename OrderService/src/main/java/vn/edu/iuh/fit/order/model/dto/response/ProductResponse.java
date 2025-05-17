package vn.edu.iuh.fit.order.model.dto.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ProductResponse {
    private Long id;
    private String name;
    private String primaryImage;  // Hình ảnh sản phẩm
    private String sku;
    private Double price;
    private String init; // Mã sản phẩm

}
