package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.*;
import vn.edu.iuh.fit.notification.model.enums.AddressType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressRequest {
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
