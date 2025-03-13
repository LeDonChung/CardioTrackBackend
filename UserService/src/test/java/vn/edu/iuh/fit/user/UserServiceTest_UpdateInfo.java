package vn.edu.iuh.fit.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.mappers.UserMapper;
import vn.edu.iuh.fit.user.model.dto.request.UserUpdateRequest;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.model.entity.User;
import vn.edu.iuh.fit.user.repositories.UserRepository;
import vn.edu.iuh.fit.user.services.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
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
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Khởi tạo đối tượng người dùng với id = 5
        user = new User();
        user.setId(5L);
        user.setFullName("Test User");
        user.setGender(vn.edu.iuh.fit.user.model.enums.Gender.Male);
        user.setDob(LocalDate.parse("1990-01-01"));

        // Khởi tạo yêu cầu cập nhật người dùng
        userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFullName("Updated User");
        userUpdateRequest.setGender(vn.edu.iuh.fit.user.model.enums.Gender.Female);
        userUpdateRequest.setDob(LocalDate.parse("1995-01-01"));
    }

    @Test
    public void testUpdateUserById_Success() throws UserException {
        // Mock trả về người dùng với id = 5
        when(userRepository.findById(5L)).thenReturn(Optional.of(user));

        // Mock save trả về người dùng đã cập nhật
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Mock phương thức chuyển đổi từ User sang UserResponse
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setFullName("Updated User");
        mockUserResponse.setGender(vn.edu.iuh.fit.user.model.enums.Gender.Female);
        mockUserResponse.setDob(LocalDate.parse("1995-01-01")); // Đảm bảo rằng ngày sinh được cập nhật

        when(userMapper.toUserResponse(any(User.class))).thenReturn(mockUserResponse);

        // Gọi phương thức cập nhật người dùng
        UserResponse result = userService.updateUserById(5L, userUpdateRequest);

        // Kiểm tra kết quả
        assertNotNull(result, "Result should not be null");
        assertEquals("Updated User", result.getFullName(), "Full name should be updated");
        assertEquals(vn.edu.iuh.fit.user.model.enums.Gender.Female, result.getGender(), "Gender should be updated");
        assertEquals(LocalDate.parse("1995-01-01"), result.getDob(), "Date of birth should be updated");  // Kiểm tra ngày sinh

        // Kiểm tra các phương thức đã được gọi
        verify(userRepository).findById(5L);
        verify(userRepository).save(any(User.class));
        verify(userMapper).toUserResponse(any(User.class));
    }


    @Test
    public void testUpdateUserById_UserNotFound() {
        // Mock trường hợp không tìm thấy người dùng
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        // Gọi phương thức và kiểm tra ngoại lệ
        assertThrows(UserException.class, () -> {
            userService.updateUserById(5L, userUpdateRequest);
        });

        // Kiểm tra phương thức đã được gọi
        verify(userRepository).findById(5L);
    }
}
