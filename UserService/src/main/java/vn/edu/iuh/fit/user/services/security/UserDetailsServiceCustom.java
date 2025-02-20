package vn.edu.iuh.fit.user.services.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.entity.Permission;
import vn.edu.iuh.fit.user.model.entity.User;
import vn.edu.iuh.fit.user.repositories.UserRepository;
import vn.edu.iuh.fit.user.utils.SystemConstraints;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsCustom userDetailsCustom = getUserDetails(username);

        if (ObjectUtils.isEmpty(userDetailsCustom)) {
            try {
                throw new UserException(SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }

        return userDetailsCustom;
    }

    public UserDetailsCustom getUserDetails(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
        }
        User userDetails = user.get();

        Set<Permission> permissions = new HashSet<>();
        userDetails.getRoles().forEach(
                r -> permissions.addAll(r.getPermissions())
        );

        return new UserDetailsCustom(
                userDetails.getUsername(),
                userDetails.getPassword(),
                permissions.stream().map(
                        r -> new SimpleGrantedAuthority(r.getCode())
                ).collect(Collectors.toList())
        );
    }
}
