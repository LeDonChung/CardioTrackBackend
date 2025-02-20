package vn.edu.iuh.fit.user.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import vn.edu.iuh.fit.user.services.security.UserDetailsCustom;

import java.security.Key;

public interface JwtService {

    Claims extractClaims(String token);

    Key getKey();

    String generateToken(UserDetailsCustom userDetailsCustom);

    boolean isValidToken(String token);
    String getCurrentUser();

    String getUsernameFromToken(String token);

}
