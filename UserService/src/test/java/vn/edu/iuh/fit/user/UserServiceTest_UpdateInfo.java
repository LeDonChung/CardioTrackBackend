package vn.edu.iuh.fit.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.mappers.UserMapper;
import vn.edu.iuh.fit.user.model.dto.request.UserUpdateRequest;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.model.entity.User;
import vn.edu.iuh.fit.user.model.enums.Gender;
import vn.edu.iuh.fit.user.repositories.UserRepository;
import vn.edu.iuh.fit.user.services.impl.UserServiceImpl;
import vn.edu.iuh.fit.user.utils.SystemConstraints;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest_UpdateInfo {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserUpdateRequest userUpdateRequest;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(5L);
        user.setFullName("Old User");
        user.setGender(Gender.Male);
        user.setDob(LocalDate.of(1990, 1, 1));

        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setUsername("updatedUser");
    }

    @Test
    void testUpdate_Success() throws Exception {
        userUpdateRequest.setFullName("Tin Tran");
        userUpdateRequest.setDob(LocalDate.of(1900, 1, 1));
        userUpdateRequest.setGender(Gender.Male);

        when(userRepository.findById(5L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setFullName("Tin Tran");
        expectedResponse.setGender(Gender.Male);
        expectedResponse.setDob(LocalDate.of(1900, 1, 1));

        when(userMapper.toUserResponse(any(User.class))).thenReturn(expectedResponse);

        UserResponse result = userService.updateUserById(5L, userUpdateRequest);

        assertEquals("Tin Tran", result.getFullName());
        assertEquals(Gender.Male, result.getGender());
    }

    @Test
    void testUpdate_Success_GenderNull() throws Exception {
        userUpdateRequest.setFullName("Tin Tran");
        userUpdateRequest.setDob(LocalDate.of(1900, 1, 1));
        userUpdateRequest.setGender(null);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setFullName("Tin Tran");
        expectedResponse.setGender(Gender.Other);
        expectedResponse.setDob(LocalDate.of(1900, 1, 1));

        when(userMapper.toUserResponse(any(User.class))).thenReturn(expectedResponse);

        UserResponse result = userService.updateUserById(5L, userUpdateRequest);

        assertEquals("Tin Tran", result.getFullName());
        assertEquals(Gender.Other, result.getGender());
    }

    @Test
    void testUpdate_FullNameEmpty() {
        userUpdateRequest.setFullName(""); // Tên đầy đủ rỗng
        userUpdateRequest.setDob(LocalDate.of(1900, 1, 1));
        userUpdateRequest.setGender(Gender.Male);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserException e = assertThrows(UserException.class, () ->
                userService.updateUserById(5L, userUpdateRequest)
        );

        assertEquals(SystemConstraints.FULL_NAME_NOT_EMPTY, e.getMessage()); // ✅ Đúng kỳ vọng
    }


    @Test
    void testUpdate_DobInFuture() {
        userUpdateRequest.setFullName("Tin Tran");
        userUpdateRequest.setDob(LocalDate.of(2025, 4, 4)); // Ngày trong tương lai

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserException e = assertThrows(UserException.class, () ->
                userService.updateUserById(5L, userUpdateRequest)
        );

        assertEquals(SystemConstraints.BIRTHDAY_NOT_GREATER_THAN_CURRENT_DATE, e.getMessage()); // ✅ Đúng kỳ vọng
    }


    @Test
    void testUpdate_AgeNotEnough() {
        userUpdateRequest.setFullName("Tin Tran");
        userUpdateRequest.setDob(LocalDate.of(2020, 4, 4)); // Dưới 14 tuổi

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserException e = assertThrows(UserException.class, () ->
                userService.updateUserById(5L, userUpdateRequest)
        );

        assertEquals(SystemConstraints.AGE_NOT_ENOUGH, e.getMessage()); // ✅ Đúng kỳ vọng
    }


    @Test
    void testUpdate_FullNameSpecialCharacter() {
        userUpdateRequest.setFullName("Tin Tran @3"); // Chứa ký tự đặc biệt
        userUpdateRequest.setDob(LocalDate.of(1900, 1, 1));
        userUpdateRequest.setGender(Gender.Male);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserException e = assertThrows(UserException.class, () ->
                userService.updateUserById(5L, userUpdateRequest)
        );

        assertEquals(SystemConstraints.FULL_NAME_NOT_SPECIAL_CHARACTERS, e.getMessage()); // ✅ Đúng kỳ vọng
    }

}
