package vn.edu.iuh.fit.inventory.enums;

import lombok.Getter;

@Getter
public enum InventoryImportStatus {
    PENDING("PENDING"),
    IMPORTED("IMPORTED"),
    CANCELLED( "CANCELLED");

    private final String value;

    InventoryImportStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
