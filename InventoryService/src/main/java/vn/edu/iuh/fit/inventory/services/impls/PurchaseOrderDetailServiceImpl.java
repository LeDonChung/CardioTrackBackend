package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.mappers.PurchaseOrderDetailMapper;
import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderDetailRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderDetailResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrderDetail;
import vn.edu.iuh.fit.inventory.repositories.PurchaseOrderDetailRepository;
import vn.edu.iuh.fit.inventory.services.PurchaseOrderDetailService;
import vn.edu.iuh.fit.inventory.services.PurchaseOrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderDetailServiceImpl implements PurchaseOrderDetailService {
    @Autowired
    private PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    @Autowired
    private PurchaseOrderDetailMapper purchaseOrderDetailMapper;

    @Override
    public List<PurchaseOrderDetailResponse> getAllPurchaseOrderDetailByPurchaseOrderId(Long purchaseOrderId) {
        List<PurchaseOrderDetail> purchaseOrderDetails = purchaseOrderDetailRepository.getAllPurchaseOrderDetailByPurchaseOrderId(purchaseOrderId);

        return purchaseOrderDetails.stream().map(purchaseOrderDetail -> purchaseOrderDetailMapper.toDto(purchaseOrderDetail)).collect(Collectors.toList());
    }

    @Override
    public void addReviewByCategory(Long purchaseOrderId, PurchaseOrderDetailRequest request) {
        List<PurchaseOrderDetail> details = purchaseOrderDetailRepository.findByPurchaseOrderIdAndCategoryId(purchaseOrderId, request.getCategory());
        for (PurchaseOrderDetail detail : details) {
            detail.setReview(request.getReview()); // Cập nhật đánh giá
        }
        purchaseOrderDetailRepository.saveAll(details);
    }

    @Override
    public void addReviewByMedicine(Long purchaseOrderId, PurchaseOrderDetailRequest request) {
        PurchaseOrderDetail detail = purchaseOrderDetailRepository.findByPurchaseOrderIdAndMedicineId(purchaseOrderId, request.getMedicine());
        detail.setReview(request.getReview()); // Cập nhật đánh giá
        purchaseOrderDetailRepository.save(detail);
    }
}
