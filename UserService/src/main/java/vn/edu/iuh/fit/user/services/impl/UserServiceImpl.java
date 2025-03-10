package vn.edu.iuh.fit.user.services.impl;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.user.exceptions.JwtException;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.jwt.JwtService;
import vn.edu.iuh.fit.user.mappers.UserMapper;
import vn.edu.iuh.fit.user.model.dto.request.AddressRequest;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.request.UserUpdateRequest;
import vn.edu.iuh.fit.user.model.dto.response.AddressResponse;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.model.entity.Address;
import vn.edu.iuh.fit.user.model.entity.AddressDetail;
import vn.edu.iuh.fit.user.model.entity.Role;
import vn.edu.iuh.fit.user.model.entity.User;
import vn.edu.iuh.fit.user.model.enums.Gender;
import vn.edu.iuh.fit.user.repositories.AddressDetailRepository;
import vn.edu.iuh.fit.user.repositories.AddressRepository;
import vn.edu.iuh.fit.user.repositories.RoleRepository;
import vn.edu.iuh.fit.user.repositories.UserRepository;
import vn.edu.iuh.fit.user.services.OTPService;
import vn.edu.iuh.fit.user.services.UserService;
import vn.edu.iuh.fit.user.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private AddressDetailRepository addressDetailRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private OTPService otpService;
    @Override
    public UserResponse register(UserRegisterRequest request) throws UserException {

        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            throw new UserException(SystemConstraints.USERNAME_IS_EXISTS);
        }

        Role role = roleRepository.findByCode("USER");


        User user = userMapper.toEntity(request);

        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setVerify(true);

        User savedUser = userRepository.save(user);



        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getMe(String token) {
        boolean isLogin = jwtService.isValidToken(token);
        if(!isLogin) {
            throw new JwtException("Token is invalid");
        }
        String username = jwtService.getUsernameFromToken(token);
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.map(user -> {
            // Lấy tên các roles của người dùng và chuyển thành Set<String>
            Set<String> roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            UserResponse userResponse = userMapper.toUserResponse(user);
            userResponse.setRoleNames(roleNames);  // Gán roleNames vào UserResponse
            return userResponse;
        }).orElse(null);
    }

    @Override
    public Boolean sendOtp(String phoneNumber) throws UserException {
        String otp = otpService.generateOTP(phoneNumber);
        log.info("Phone: " + phoneNumber + " OTP: " + otp);
        return otp != null;
    }

    @Override
    public Boolean verifyOtp(String phoneNumber, String otp) throws UserException {
        return otpService.validateOTP(phoneNumber, otp);
    }

    @Override
    public Long findIdByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByUsername(phoneNumber);
        return userOptional.map(User::getId).orElse(null);
    }

    @Override
    public AddressResponse addAddress(AddressRequest address) throws UserException {
        AddressDetail addressDetail = new AddressDetail();
        if(address.getUserId() != null) {
            Optional<User> userOptional = userRepository.findById(address.getUserId());
            if(userOptional.isEmpty()) {
                throw new UserException(SystemConstraints.USER_NOT_FOUND);
            }
            addressDetail.setUser(userOptional.get());
        }

        Address a = new Address();
        a.setDistrict(address.getDistrict());
        a.setProvince(address.getProvince());
        a.setWard(address.getWard());
        a.setStreet(address.getStreet());

        a = addressRepository.save(a);

        addressDetail.setAddress(a);
        addressDetail.setFullName(address.getFullName());
        addressDetail.setPhoneNumber(address.getPhoneNumber());
        addressDetail.setAddressType(address.getAddressType());
        addressDetail.setOrderId(address.getOrderId() != null ? address.getOrderId() : null);

        addressDetail = addressDetailRepository.save(addressDetail);

        return getAddressResponse(addressDetail);
    }

    private static AddressResponse getAddressResponse(AddressDetail addressDetail) {

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setId(addressDetail.getId());
        addressResponse.setDistrict(addressDetail.getAddress().getDistrict());
        addressResponse.setProvince(addressDetail.getAddress().getProvince());
        addressResponse.setWard(addressDetail.getAddress().getWard());
        addressResponse.setStreet(addressDetail.getAddress().getStreet());
        addressResponse.setAddressType(addressDetail.getAddressType());
        addressResponse.setFullName(addressDetail.getFullName());
        addressResponse.setPhoneNumber(addressDetail.getPhoneNumber());
        addressResponse.setUserId(addressDetail.getUser() != null ? addressDetail.getUser().getId() : null);
        addressResponse.setOrderId(addressDetail.getOrderId() != null ? addressDetail.getOrderId() : null);
        return addressResponse;
    }

    @Override
    public UserResponse getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> userMapper.toUserResponse(user)).orElse(null);
    }

    //hàm tín viết
    @Override
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        List<AddressDetail> addressDetails = addressDetailRepository.findByUserId(userId);
        return addressDetails.stream()
                .map(UserServiceImpl::getAddressResponse) // Chuyển đổi sang DTO
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUserById(Long id, UserUpdateRequest userUpdateRequest) throws UserException {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            throw new UserException(SystemConstraints.USER_NOT_FOUND);
        }
        User user = userOptional.get();
        user.setFullName(userUpdateRequest.getFullName());
        user.setGender(userUpdateRequest.getGender());
        user.setDob(userUpdateRequest.getDob());
        user.setUsername(userUpdateRequest.getUsername());
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public Boolean deleteAddressById(Long id) throws UserException {
        Optional<AddressDetail> addressDetailOptional = addressDetailRepository.findById(id);
        if(addressDetailOptional.isEmpty()) {
            throw new UserException(SystemConstraints.ADDRESS_NOT_FOUND);
        }
        addressDetailRepository.deleteById(id);
        return true;
    }

    @Override
    public AddressResponse updateAddressById(Long id, AddressRequest request) throws UserException {
        Optional<AddressDetail> addressDetailOptional = addressDetailRepository.findById(id);
        if(addressDetailOptional.isEmpty()) {
            throw new UserException(SystemConstraints.ADDRESS_NOT_FOUND);
        }
        AddressDetail addressDetail = addressDetailOptional.get();
        Address address = addressDetail.getAddress();
        address.setDistrict(request.getDistrict());
        address.setProvince(request.getProvince());
        address.setWard(request.getWard());
        address.setStreet(request.getStreet());
        address = addressRepository.save(address);

        addressDetail.setAddress(address);
        addressDetail.setFullName(request.getFullName());
        addressDetail.setPhoneNumber(request.getPhoneNumber());
        addressDetail.setAddressType(request.getAddressType());
        addressDetail.setOrderId(request.getOrderId() != null ? request.getOrderId() : null);


        addressDetail = addressDetailRepository.save(addressDetail);

        return getAddressResponse(addressDetail);
    }


}
