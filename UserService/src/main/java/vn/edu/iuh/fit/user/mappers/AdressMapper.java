package vn.edu.iuh.fit.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdressMapper {
    // AddressDTO toAddressDTO(Address address);

    // Address toAddress(AddressDTO addressDTO);
}
