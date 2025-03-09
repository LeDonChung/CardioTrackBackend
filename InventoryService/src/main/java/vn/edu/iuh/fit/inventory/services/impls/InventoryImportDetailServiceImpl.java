package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.mappers.InventoryImportDetailMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportDetailResponse;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImportDetail;
import vn.edu.iuh.fit.inventory.repositories.InventoryImportDetailRepository;
import vn.edu.iuh.fit.inventory.services.InventoryImportDetailService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryImportDetailServiceImpl implements InventoryImportDetailService {
    @Autowired
    private InventoryImportDetailRepository inventoryImportDetailRepository;

    @Autowired
    private InventoryImportDetailMapper inventoryImportDetailMapper;

    @Override
    public List<InventoryImportDetailResponse> getAllPInventoryImportDetailByImportId(Long inventoryImportId) {
        List<InventoryImportDetail> inventoryImportDetails = inventoryImportDetailRepository.getAllPagesInventoryImportDetail(inventoryImportId);

        return inventoryImportDetails.stream().map(inventoryImportDetail -> inventoryImportDetailMapper.toDto(inventoryImportDetail)).collect(Collectors.toList());

    }
}
