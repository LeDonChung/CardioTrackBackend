package vn.edu.iuh.fit.student.thanhtuyen.chatservice.services;

import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto saveUser(UserDto userDto);
}
