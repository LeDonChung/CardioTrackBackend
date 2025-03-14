package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.inventory.exceptions.SupplierException;
import vn.edu.iuh.fit.inventory.mappers.SupplierMapper;
import vn.edu.iuh.fit.inventory.models.dtos.requests.SupplierRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.SupplierResponse;
import vn.edu.iuh.fit.inventory.models.entities.Supplier;
import vn.edu.iuh.fit.inventory.repositories.SupplierRepository;
import vn.edu.iuh.fit.inventory.services.SupplierService;
import vn.edu.iuh.fit.inventory.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public SupplierResponse save(SupplierRequest request) throws SupplierException {
        Supplier supplier = null;

        if(request.getId() == null) {
            supplier = supplierMapper.toEntity(request);
        } else {
            Optional<Supplier> olSupplier = supplierRepository.findById(request.getId());
            if(olSupplier.isEmpty()) {
                throw new SupplierException(SystemConstraints.SUPPLIER_NOT_FOUND);
            } else {
                supplier = supplierMapper.partialUpdate(request, olSupplier.get());
            }
        }

        supplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(supplier);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();

        return suppliers.stream().map(supplier -> supplierMapper.toDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public SupplierResponse getSupplierById(Long id) throws SupplierException {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if(supplierOptional.isPresent()) {
            return supplierMapper.toDto(supplierOptional.get());
        }
        throw new SupplierException(SystemConstraints.SUPPLIER_NOT_FOUND);
    }

    @Override
    public List<SupplierResponse> getSupplierByName(String name) throws SupplierException {
        List<Supplier> suppliers = supplierRepository.getSupplierByNameLike(name);

        return suppliers.stream().map(supplier -> supplierMapper.toDto(supplier)).toList();

    }
}
