package vn.edu.iuh.fit.user;

import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.services.UserService;
import vn.edu.iuh.fit.user.utils.SystemConstraints;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testValidPartition() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("0867713557")
                        .fullName("Lê Đôn Chủng")
                        .password("1234567")
                        .rePassword("1234567")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
            assert userResponse != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInvalidPartition01() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("0396172224")
                        .fullName("Lê Đôn Chủng")
                        .password("1234567")
                        .rePassword("1234567")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
        } catch (UserException e) {
            assert e.getMessage().equalsIgnoreCase(SystemConstraints.USERNAME_IS_EXISTS);
        }
    }

    @Test
    void testInvalidPartition02() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("")
                        .fullName("Lê Đôn Chủng")
                        .password("1234567")
                        .rePassword("1234567")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
        } catch (UserException e) {
            assert e.getMessage().equalsIgnoreCase("Vui lòng nhập số điện thoại.");
        }
    }

    @Test
    void testInvalidPartition03() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("")
                        .fullName("88174917271")
                        .password("1234567")
                        .rePassword("1234567")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
        } catch (UserException e) {
            assert e.getMessage().equalsIgnoreCase("Số điện thoại không hợp lệ.");
        }
    }

    @Test
    void testInvalidPartition04() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("0396172224")
                        .fullName("Lê Đôn Chủng")
                        .password("12345")
                        .rePassword("12345")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
        } catch (UserException e) {
            assert e.getMessage().equalsIgnoreCase("Mật khẩu phải lớn hơn 5 ký tự.");
        }
    }

    @Test
    void testInvalidPartition05() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("0396172224")
                        .fullName("Lê Đôn Chủng")
                        .password("1234567")
                        .rePassword("123456")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
        } catch (UserException e) {
            assert e.getMessage().equalsIgnoreCase("Mật khẩu và xác nhận mật khẩu không trùng khớp.");
        }
    }

    @Test
    void testInvalidPartition06() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("0396172224")
                        .fullName("")
                        .password("1234567")
                        .rePassword("123456")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
        } catch (UserException e) {
            assert e.getMessage().equalsIgnoreCase("Vui lòng nhập họ và tên.");
        }
    }

    @Test
    void testValidBoundary() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("0396172224")
                        .fullName("Lê Đôn Chủng")
                        .password("123456")
                        .rePassword("123456")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
            assert userResponse != null;
        } catch (UserException e) {
            assert false;
        }
    }

    @Test
    void testInValidBoundary() {
        UserRegisterRequest userRegisterRequest =
                UserRegisterRequest.builder()
                        .username("0396172224")
                        .fullName("Lê Đôn Chủng")
                        .password("12345")
                        .rePassword("12345")
                        .build();

        try {
            UserResponse userResponse = userService.register(userRegisterRequest);
        } catch (UserException e) {
            assert e.getMessage().equalsIgnoreCase("Mật khẩu phải lớn hơn 5 ký tự.");
        }
    }
}
