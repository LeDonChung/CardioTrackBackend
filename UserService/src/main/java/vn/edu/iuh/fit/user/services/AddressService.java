package vn.edu.iuh.fit.user.services;

import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> findAddressByUserId(Long id);
}
