package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.enums.InventoryImportStatus;
import vn.edu.iuh.fit.inventory.exceptions.InventoryImportException;
import vn.edu.iuh.fit.inventory.mappers.InventoryImportMapper;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryImportRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryDetailResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportResponse;
import vn.edu.iuh.fit.inventory.models.entities.InventoryDetail;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;
import vn.edu.iuh.fit.inventory.repositories.InventoryImportRepository;
import vn.edu.iuh.fit.inventory.services.InventoryImportService;
import vn.edu.iuh.fit.inventory.utils.SystemConstraints;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryImportServiceImpl implements InventoryImportService {
    @Autowired
    private InventoryImportRepository inventoryImportRepository;

    @Autowired
    private InventoryImportMapper inventoryImportMapper;

    //Thêm phiếu nhập
    @Override
    public InventoryImportResponse save(InventoryImportRequest request) throws InventoryImportException {
        InventoryImport inventoryImport = inventoryImportMapper.toEntity(request);
        inventoryImport.setStatus(InventoryImportStatus.PENDING);

        InventoryImport finalInventoryImport = inventoryImport;
        inventoryImport.getInventoryImportDetails().forEach(inventoryImportDetail -> {
            inventoryImportDetail.setInventoryImport(finalInventoryImport);
        });

        inventoryImport = inventoryImportRepository.save(inventoryImport);
        return inventoryImportMapper.toDto(inventoryImport);
    }

    //Thay đổi trạng thái của phiếu nhập
    @Override
    public InventoryImportResponse changeStatus(Long id, InventoryImportStatus invenotryImport) throws InventoryImportException {
        Optional<InventoryImport> inventoryImportOptional = inventoryImportRepository.findById(id);
        if(inventoryImportOptional.isPresent()) {
            InventoryImport inventoryImportEntity = inventoryImportOptional.get();
            inventoryImportEntity.setStatus(invenotryImport);
            inventoryImportRepository.save(inventoryImportEntity);
            return inventoryImportMapper.toDto(inventoryImportEntity);
        }
        throw new InventoryImportException(SystemConstraints.INVENTORYIMPORT_NOT_FOUND);
    }

    @Override
    public PageDTO<InventoryImportResponse> getPagesInventoryImport(int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if(sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        Set<InventoryImport> inventoryImports = inventoryImportRepository.findAll(pageable).toSet();

        Set<InventoryImportResponse> inventoryImportResponses = inventoryImports.stream().map(inventoryImport -> inventoryImportMapper.toDto(inventoryImport)).collect(Collectors.toSet());

        return PageDTO.<InventoryImportResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(inventoryImportResponses)
                .build();
    }
}
