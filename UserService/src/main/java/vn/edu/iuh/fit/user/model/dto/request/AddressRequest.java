package vn.edu.iuh.fit.user.model.dto.request;

import lombok.*;
import vn.edu.iuh.fit.user.enums.AddressType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
