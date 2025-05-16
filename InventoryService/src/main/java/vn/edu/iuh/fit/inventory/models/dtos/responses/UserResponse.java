package vn.edu.iuh.fit.inventory.models.dtos.responses;

import lombok.*;
import vn.edu.iuh.fit.inventory.enums.UserInventoryRole;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String password;
    private Long id;
    private Long inventory;
    private UserInventoryRole role;
}
