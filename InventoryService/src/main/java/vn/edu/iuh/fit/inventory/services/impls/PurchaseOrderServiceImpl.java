package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.enums.PurchaseOrderStatus;
import vn.edu.iuh.fit.inventory.exceptions.PurchaseOrderException;
import vn.edu.iuh.fit.inventory.mappers.PurchaseOrderMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.PurchaseOrderRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.PurchaseOrderResponse;
import vn.edu.iuh.fit.inventory.models.entities.PurchaseOrder;
import vn.edu.iuh.fit.inventory.repositories.PurchaseOrderRepository;
import vn.edu.iuh.fit.inventory.services.PurchaseOrderService;
import vn.edu.iuh.fit.inventory.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Override
    public PageDTO<PurchaseOrderResponse> getAllPurchaseOrder(int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if (sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        // Lấy dữ liệu phân trang từ repository
        Page<PurchaseOrder> purchaseOrderPage = purchaseOrderRepository.findAll(pageable);
        List<PurchaseOrder> inventoryDetails = purchaseOrderPage.getContent();

        List<PurchaseOrderResponse> purchaseOrderResponses = inventoryDetails.stream()
                .map(purchaseOrder -> purchaseOrderMapper.toDto(purchaseOrder))
                .collect(Collectors.toList());

        // Tạo và trả về PageDTO với thông tin phân trang
        return PageDTO.<PurchaseOrderResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(purchaseOrderResponses)
                .build();
    }

    @Override
    public PurchaseOrderResponse save(PurchaseOrderRequest request) throws PurchaseOrderException {
        // Chuyển request thành đối tượng PurchaseOrder
        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(request);
        purchaseOrder.setStatus(PurchaseOrderStatus.PENDING);

        // Gán PurchaseOrder cho từng PurchaseOrderDetail
        PurchaseOrder finalPurchaseOrder = purchaseOrder;
        purchaseOrder.getPurchaseOrderDetails().forEach(purchaseOrderDetail -> {
            purchaseOrderDetail.setPurchaseOrder(finalPurchaseOrder);
        });

        // Sử dụng merge thay vì save để tránh đối tượng detached
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        // Trả về DTO của PurchaseOrder
        return purchaseOrderMapper.toDto(purchaseOrder);
    }

    @Override
    public PageDTO<PurchaseOrderResponse> getAllPendingPurchaseOrder(int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if (sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        // Lấy dữ liệu phân trang từ repository
        Page<PurchaseOrder> purchaseOrderPage = purchaseOrderRepository.getAllPendingPurchaseOrder(pageable);
        List<PurchaseOrder> inventoryDetails = purchaseOrderPage.getContent();

        List<PurchaseOrderResponse> purchaseOrderResponses = inventoryDetails.stream()
                .map(purchaseOrder -> purchaseOrderMapper.toDto(purchaseOrder))
                .collect(Collectors.toList());

        // Tạo và trả về PageDTO với thông tin phân trang
        return PageDTO.<PurchaseOrderResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(purchaseOrderResponses)
                .build();
    }

    @Override
    public PurchaseOrderResponse changeStatus(Long id, PurchaseOrderStatus status) throws PurchaseOrderException {
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderRepository.findById(id);
        if(purchaseOrderOptional.isPresent()) {
            PurchaseOrder purchaseOrderEntity = purchaseOrderOptional.get();
            purchaseOrderEntity.setStatus(status);
            purchaseOrderRepository.save(purchaseOrderEntity);
            return purchaseOrderMapper.toDto(purchaseOrderEntity);
        }
        throw new PurchaseOrderException(SystemConstraints.PURCHASEORDER_NOT_FOUND);
    }
}
