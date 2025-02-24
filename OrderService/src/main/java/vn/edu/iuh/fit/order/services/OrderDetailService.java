package vn.edu.iuh.fit.order.services;

import vn.edu.iuh.fit.order.model.dto.request.OrderDetailRequest;
import vn.edu.iuh.fit.order.model.dto.request.OrderRequest;
import vn.edu.iuh.fit.order.model.dto.response.OrderDetailResponse;
import vn.edu.iuh.fit.order.model.dto.response.OrderResponse;
import vn.edu.iuh.fit.order.model.entities.OrderDetail;

public interface OrderDetailService {

    // Thêm OrderDetail
    OrderDetailResponse save(OrderDetailRequest request);
}
