package vn.edu.iuh.fit.inventory.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.inventory.enums.UserInventoryRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    private String username;
    private String password;
    private Long id;
    private Long inventory;
    private UserInventoryRole role;
}

