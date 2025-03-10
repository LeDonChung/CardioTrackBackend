package vn.edu.iuh.fit.inventory.enums;

import lombok.Getter;

@Getter
public enum UserInventoryRole {
    MANAGEMENT("ADMIN"),
    STAFF("STAFF"),
    VIEWER("VIEWER");

    private final String value;

    UserInventoryRole(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
