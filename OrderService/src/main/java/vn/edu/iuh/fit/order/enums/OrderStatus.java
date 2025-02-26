package vn.edu.iuh.fit.order.enums;

public enum OrderStatus {
    PENDING,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELED,
    RETURNED,
    PROCESSING,
    FAILED
}

//Pending: Đang chờ xử lý, chưa được xác nhận.
//Confirmed: Đã xác nhận, đang chờ vận chuyển.
//Shipped: Đã được vận chuyển, đang trên đường đến khách hàng.
//Delivered: Đã giao hàng thành công.
//Cancelled: Đơn hàng đã bị hủy bỏ.
//Refunded: Đã hoàn tiền cho đơn hàng.
//Processing: Đơn hàng đang được xử lý.
//Failed: Đơn hàng không thành công, có thể do thanh toán thất bại hoặc lý do khác.
//Returned: Đơn hàng đã được trả lại.
