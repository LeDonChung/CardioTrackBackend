package vn.edu.iuh.fit.user.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.edu.iuh.fit.user.exceptions.UserException;
import vn.edu.iuh.fit.user.model.entity.Permission;
import vn.edu.iuh.fit.user.model.entity.Role;
import vn.edu.iuh.fit.user.model.entity.User;
import vn.edu.iuh.fit.user.repositories.UserRepository;
import vn.edu.iuh.fit.user.utils.SystemConstraints;

import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Start actual authentication");
        final String username = authentication.getName();

        final String password = authentication.getCredentials().toString();

        Optional<User> user;
        try {
            user = userRepository.findByUsername(username);
        }catch (Exception e){
            try {
                throw new UserException(SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
            } catch (UserException ex) {
                throw new RuntimeException(ex);
            }
        }

        if(user.isEmpty()) {
            try {
                throw new UserException(SystemConstraints.INVALID_USERNAME_OR_PASSWORD);
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }
        Set<Permission> permissions = new HashSet<>();
        user.get().getRoles().forEach(
                r -> permissions.addAll(r.getPermissions())
        );

        final List<GrantedAuthority> authorities = getAuthorities(user.get().getRoles());

        final Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);

        log.info("End actual authentication");
        return auth;
    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> result = new ArrayList<>();
        Set<String> permissions = new HashSet<>();

        if(!ObjectUtils.isEmpty(roles)){
            roles.forEach( r-> {
                r.getPermissions().forEach(p->{
                    permissions.add(p.getCode());
                });
            });
        }

        permissions.forEach(p->{
            result.add(new SimpleGrantedAuthority(p));
        });
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
