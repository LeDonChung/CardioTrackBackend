package vn.edu.iuh.fit.user.services.impl;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.user.exceptions.JwtException;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.jwt.JwtService;
import vn.edu.iuh.fit.user.mappers.UserMapper;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.model.entity.Role;
import vn.edu.iuh.fit.user.model.entity.User;
import vn.edu.iuh.fit.user.repositories.RoleRepository;
import vn.edu.iuh.fit.user.repositories.UserRepository;
import vn.edu.iuh.fit.user.services.UserService;
import vn.edu.iuh.fit.user.utils.SystemConstraints;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
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
        return userOptional.map(user -> userMapper.toUserResponse(user)).orElse(null);
    }
}
