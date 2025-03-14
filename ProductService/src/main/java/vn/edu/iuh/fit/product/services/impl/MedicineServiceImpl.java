package vn.edu.iuh.fit.product.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.exceptions.BrandException;
import vn.edu.iuh.fit.product.exceptions.CategoryException;
import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.mappers.MedicineMapper;
import vn.edu.iuh.fit.product.models.dtos.PageDTO;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineSearchRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.product.models.entities.Brand;
import vn.edu.iuh.fit.product.models.entities.Category;
import vn.edu.iuh.fit.product.models.entities.Medicine;
import vn.edu.iuh.fit.product.repositories.BrandRepository;
import vn.edu.iuh.fit.product.repositories.CategoryRepository;
import vn.edu.iuh.fit.product.repositories.MedicineRepository;
import vn.edu.iuh.fit.product.repositories.specifications.MedicineFilterSpecification;
import vn.edu.iuh.fit.product.services.MedicineService;
import vn.edu.iuh.fit.product.utils.SystemConstraints;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineMapper medicineMapper;


    @Override
    public MedicineResponse save(MedicineRequest medicineRequest) throws MedicineException {
        Medicine medicine = null;
        if(medicineRequest.getId() == null) {
            medicine = medicineMapper.toEntity(medicineRequest);
            medicine.setStatus(1);
        } else {
            Optional<Medicine> medicineOptional = medicineRepository.findById(medicineRequest.getId());
            if (medicineOptional.isEmpty()) {
                throw new MedicineException(SystemConstraints.MEDICINE_NOT_FOUND);
            }

            medicine = medicineMapper.partialUpdate(medicineRequest, medicineOptional.get());
        }

        // check brand throw -> brand not found
        // check category throw -> category not found
        // check tag throw -> tag not found
        // check specification throw -> specification not found
        medicine = medicineRepository.save(medicine);

        return medicineMapper.toDto(medicine);
    }

    @Override
    public List<MedicineResponse> getAllMedicines() {
        return null;
    }

    @Override
    public MedicineResponse updateStatusById(Long id, int status) throws MedicineException {
        Optional<Medicine> medicineOptional = medicineRepository.findById(id);
        if (medicineOptional.isEmpty()) {
            throw new MedicineException(SystemConstraints.MEDICINE_NOT_FOUND);
        }

        Medicine medicine = medicineOptional.get();

        medicine.setStatus(status);

        medicine = medicineRepository.save(medicine);

        return medicineMapper.toDto(medicine);
    }

    @Override
    public MedicineResponse getMedicineById(Long id) throws MedicineException {
        Optional<Medicine> medicineOptional = medicineRepository.findById(id);
        if (medicineOptional.isEmpty()) {
            throw new MedicineException(SystemConstraints.MEDICINE_NOT_FOUND);
        }

        return medicineMapper.toDto(medicineOptional.get());
    }

    @Override
    public PageDTO<MedicineResponse> getPages(int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if(sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        Set<Medicine> medicines = medicineRepository.findAll(pageable).toSet();

        Set<MedicineResponse> medicineResponses = medicines.stream().map(medicine -> medicineMapper.toDto(medicine)).collect(Collectors.toSet());

        return PageDTO.<MedicineResponse>builder()
                .page(page)
                .size(size)
                .totalPage(medicineRepository.findAll(pageable).getTotalPages())
                .sortBy(sortBy)
                .sortName(sortName)
                .data(medicineResponses)
                .build();
    }

    @Override
    public boolean isExists(Long id) {
        return medicineRepository.existsById(id);
    }

    @Override
    public PageDTO<MedicineResponse> search(MedicineSearchRequest request, int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Medicine> pageMedicine = medicineRepository.findAll(MedicineFilterSpecification.filter(request, sortBy, sortName), pageable);

        LinkedHashSet<MedicineResponse> medicines = pageMedicine.stream()
                .map(medicineMapper::toDto)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return PageDTO.<MedicineResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(medicines)
                .totalPage(pageMedicine.getTotalPages())
                .build();
    }

    @Override
    public List<MedicineResponse> getAll() {
        return medicineRepository.findAll().stream().map(medicineMapper::toDto).collect(Collectors.toList());
    }


}
