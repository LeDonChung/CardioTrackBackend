package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.enums.PurchaseOrderStatus;
import vn.edu.iuh.fit.inventory.exceptions.PurchaseOrderException;
import vn.edu.iuh.fit.inventory.mappers.PurchaseOrderMapper;
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
    public List<PurchaseOrderResponse> getAllPendingPurchaseOrder() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.getAllPendingPurchaseOrder();

        return purchaseOrders.stream().map(purchaseOrder -> purchaseOrderMapper.toDto(purchaseOrder)).collect(Collectors.toList());
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
