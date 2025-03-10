package vn.edu.iuh.fit.inventory.enums;

import lombok.Getter;

@Getter
public enum SheftStatus {
    EMPTY("EMPTY"),
    FULL("FULL");

    private final String value;

    SheftStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
