package vn.edu.iuh.fit.inventory.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.inventory.enums.UserInventoryRole;

@Getter
@Setter
@Entity
@Table(name = "user_inventories")
public class UserInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserInventoryRole role;
}
