package vn.edu.iuh.fit.inventory.enums;

import lombok.Getter;

@Getter
public enum InventoryStatus {
    EMPTY("EMPTY"),
    APPROXIMATELY_A_QUARTER("APPROXIMATELY_A_QUARTER"),
    APPROXIMATELY_HALF("APPROXIMATELY_HALF"),
    APPROXIMATELY_THREE_QUARTER("APPROXIMATELY_THREE_QUARTER"),
    FULLED("FULLED");
    private final String value;


    InventoryStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
