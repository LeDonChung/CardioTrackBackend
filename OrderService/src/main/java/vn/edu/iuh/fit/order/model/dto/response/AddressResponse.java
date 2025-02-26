package vn.edu.iuh.fit.order.model.dto.response;

import lombok.*;
import vn.edu.iuh.fit.order.enums.AddressType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponse {
    private Long id;
    private String district;
    private String province;
    private String ward;
    private String street;
    private AddressType addressType;
    private String fullName;
    private String phoneNumber;
    private Long userId;
    private Long orderId;
}
