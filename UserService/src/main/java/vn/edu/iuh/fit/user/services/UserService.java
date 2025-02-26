package vn.edu.iuh.fit.user.services;

import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;

public interface UserService {
    UserResponse register(UserRegisterRequest request) throws UserException;

    UserResponse getMe(String substring);

    Boolean sendOtp(String phoneNumber) throws UserException;

    Boolean verifyOtp(String phoneNumber, String otp) throws UserException;

    Long findIdByPhoneNumber(String phoneNumber);

    AddressResponse addAddress(AddressRequest address) throws UserException;

    UserResponse getUserById(Long id);
}
