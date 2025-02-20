package vn.edu.iuh.fit.auth.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.iuh.fit.auth.client.UserServiceClient;
import vn.edu.iuh.fit.auth.model.dto.Token;
import vn.edu.iuh.fit.auth.model.dto.response.BaseResponse;
import vn.edu.iuh.fit.auth.model.dto.response.UserResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomBearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserServiceClient userServiceClient;


    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest request,
                                    @NonNull final HttpServletResponse response,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {


        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Token.isBearerToken(authorizationHeader)) {
            final String jwt = Token.getJwt(authorizationHeader);
            userServiceClient.validateToken(jwt);
        }
        filterChain.doFilter(request, response);

    }

}
