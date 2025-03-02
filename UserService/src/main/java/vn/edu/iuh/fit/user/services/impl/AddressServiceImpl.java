package vn.edu.iuh.fit.user.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.model.entity.Address;
import vn.edu.iuh.fit.user.model.entity.AddressDetail;
import vn.edu.iuh.fit.user.repositories.AddressDetailRepository;
import vn.edu.iuh.fit.user.repositories.AddressRepository;
import vn.edu.iuh.fit.user.services.AddressService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDetailRepository addressDetailRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<AddressResponse> findAddressByUserId(Long id) {
        List<AddressDetail> addressDetails = addressDetailRepository.findByUserId(id);

        List<AddressResponse> addressResponses = new ArrayList<>();

        if (addressDetails == null) {
            return null;
        } else {
            for (AddressDetail addressDetail : addressDetails) {

                Optional<Address> address = addressRepository.findById(addressDetail.getAddress().getId());

                AddressResponse addressResponse = new AddressResponse();
                addressResponse.setId(addressDetail.getAddress().getId());

                addressResponse.setProvince(address.get().getProvince());
                addressResponse.setDistrict(address.get().getDistrict());
                addressResponse.setWard(address.get().getWard());
                addressResponse.setStreet(address.get().getStreet());
                addressResponse.setAddressType(addressDetail.getAddressType());
                addressResponse.setFullName(addressDetail.getFullName());
                addressResponse.setPhoneNumber(addressDetail.getPhoneNumber());
                addressResponse.setUserId(addressDetail.getUser().getId());
                addressResponse.setOrderId(addressDetail.getOrderId());
                addressResponses.add(addressResponse);
            }

            if (addressResponses == null) {
                return new ArrayList<>();
            }
            return addressResponses;
        }
    }
}
