package vn.edu.iuh.fit.notification.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String district;
    private String province;
    private String ward;
    private String street;
}
