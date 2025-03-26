package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.inventory.enums.UserInventoryRole;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInventoryRequest {
    private Long id;
    private Long user;
    private Long inventory;
    private UserInventoryRole role;
}
