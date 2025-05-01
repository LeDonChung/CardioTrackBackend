package vn.edu.iuh.fit.user.services;

import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.request.UserUpdateRequest;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.model.entity.Address;

import java.util.List;

public interface UserService {
    UserResponse register(UserRegisterRequest request) throws UserException;

    UserResponse getMe(String substring);

    Boolean sendOtp(String phoneNumber);

    Boolean verifyOtp(String phoneNumber, String otp) throws UserException;

    Long findIdByPhoneNumber(String phoneNumber);

    AddressResponse addAddress(AddressRequest address) throws UserException;

    UserResponse getUserById(Long id);


    List<AddressResponse> getAddressesByUserId(Long userId);



    //hàm update user từ user id
    UserResponse updateUserById(Long id, UserUpdateRequest request) throws UserException;
    //hàm xoa địa chỉ theo id address
    Boolean deleteAddressById(Long id) throws UserException;
    //hàm update address theo id address
    AddressResponse updateAddressById(Long id, AddressRequest request) throws UserException;

    //hàm lấy địa chỉ theo id address
    AddressResponse getAddressById_Address(Long id) throws UserException;
}
