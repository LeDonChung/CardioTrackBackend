package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailRequest {
    private String addressType;
    private AddressRequest address;
    private String fullName;
    private String phoneNumber;
}
