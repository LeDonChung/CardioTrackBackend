package vn.edu.iuh.fit.product.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.exceptions.BrandException;
import vn.edu.iuh.fit.product.mappers.BrandMapper;
import vn.edu.iuh.fit.product.models.dtos.PageDTO;
import vn.edu.iuh.fit.product.models.dtos.requests.BrandRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BrandResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineResponse;
import vn.edu.iuh.fit.product.models.entities.Brand;
import vn.edu.iuh.fit.product.models.entities.Medicine;
import vn.edu.iuh.fit.product.repositories.BrandRepository;
import vn.edu.iuh.fit.product.services.BrandService;
import vn.edu.iuh.fit.product.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    //save
    @Override
    public BrandResponse save(BrandRequest request) throws BrandException {
        Brand brand = null;
        if(request.getId() == null) {
            brand = brandMapper.toEntity(request);
        } else {
            Optional<Brand> oldBrand = brandRepository.findById(request.getId());
            if(oldBrand.isEmpty()) {
                throw new BrandException(SystemConstraints.BRAND_NOT_FOUND);
            } else {
                brand = brandMapper.partialUpdate(request, oldBrand.get());
            }
        }
        brand = brandRepository.save(brand);
        return brandMapper.toResponse(brand);
    }

    //get by id
    @Override
    public BrandResponse getBrandById(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if(brand.isEmpty()) {
            throw new BrandException(SystemConstraints.BRAND_NOT_FOUND);
        }
        return brandMapper.toResponse(brand.get());
    }

    @Override
    public List<BrandResponse> getAllBrand() throws BrandException {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream().map(brandMapper::toResponse).toList();
}
    public PageDTO<BrandResponse> getAll(int page, int size, String sortBy, String sortName) {
        Pageable pageable = PageRequest.of(page, size);
        if(sortBy != null && sortName != null) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortName), sortBy));
        }

        Set<Brand> brands = brandRepository.findAll(pageable).toSet();

        Set<BrandResponse> brandResponses = brands.stream().map(medicine -> brandMapper.toResponse(medicine)).collect(Collectors.toSet());

        return PageDTO.<BrandResponse>builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortName(sortName)
                .data(brandResponses)
                .build();
    }
}
