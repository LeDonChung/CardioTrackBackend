package vn.edu.iuh.fit.inventory.models.dtos.responses;

import lombok.*;
import vn.edu.iuh.fit.inventory.enums.UserInventoryRole;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInventoryResponse {
    private Long id;
    private String name;
    private Long user;
    private Long inventory;
    private UserInventoryRole role;
}
