package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.exceptions.BrandException;
import vn.edu.iuh.fit.product.models.dtos.requests.BrandRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse save(BrandRequest request) throws BrandException;

    //get by id
    BrandResponse getBrandById(Long id) throws BrandException;

    //get all
    List<BrandResponse> getAllBrand() throws BrandException;
}
