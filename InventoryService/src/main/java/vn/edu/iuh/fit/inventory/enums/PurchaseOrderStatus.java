package vn.edu.iuh.fit.inventory.enums;

import lombok.Getter;

@Getter
public enum PurchaseOrderStatus {
    PENDING("PENDING"),//Đang xử lý
    APPROVED("APPROVED"), // Chấp thuân
    REJECTED("REJECTED"), // Từ chối
    CANCELED("CANCELED"); // Hủy

    private final String value;

    PurchaseOrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
