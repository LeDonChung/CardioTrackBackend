package vn.edu.iuh.fit.user.services;

import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;

public interface UserService {
    UserResponse register(UserRegisterRequest request) throws UserException;

    UserResponse getMe(String substring);
}
