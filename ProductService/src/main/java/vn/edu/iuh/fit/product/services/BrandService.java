package vn.edu.iuh.fit.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.model.entities.Brand;
import vn.edu.iuh.fit.product.repositories.BrandRepository;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    //get by id
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }
}
