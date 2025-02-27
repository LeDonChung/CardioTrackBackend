package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.exceptions.BrandException;
import vn.edu.iuh.fit.product.models.dtos.PageDTO;
import vn.edu.iuh.fit.product.models.dtos.requests.BrandRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BrandResponse;

public interface BrandService {
    BrandResponse save(BrandRequest request) throws BrandException;

    //get by id
    BrandResponse getBrandById(Long id) throws BrandException;

    PageDTO<BrandResponse> getAll(int page, int size, String sortBy, String sortType);
}
