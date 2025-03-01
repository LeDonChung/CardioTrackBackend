package vn.edu.iuh.fit.inventory.enums;

import lombok.Getter;

@Getter
public enum UserInventoryRole {
    MANAGEMENT("ADMIN"),
    CASHIER("CASHIER"),
    LOADING("LOADING"),
    HANDLING("HANDLING");

    private final String value;

    UserInventoryRole(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
