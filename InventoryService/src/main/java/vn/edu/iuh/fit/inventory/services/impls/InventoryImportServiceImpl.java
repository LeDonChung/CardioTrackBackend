package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.enums.InventoryImportStatus;
import vn.edu.iuh.fit.inventory.exceptions.InventoryImportException;
import vn.edu.iuh.fit.inventory.mappers.InventoryImportMapper;
import vn.edu.iuh.fit.inventory.models.dtos.requests.InventoryImportRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.InventoryImportResponse;
import vn.edu.iuh.fit.inventory.models.entities.InventoryImport;
import vn.edu.iuh.fit.inventory.repositories.InventoryImportRepository;
import vn.edu.iuh.fit.inventory.services.InventoryImportService;
import vn.edu.iuh.fit.inventory.utils.SystemConstraints;

import java.time.LocalDate;
import java.util.Optional;

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
}
