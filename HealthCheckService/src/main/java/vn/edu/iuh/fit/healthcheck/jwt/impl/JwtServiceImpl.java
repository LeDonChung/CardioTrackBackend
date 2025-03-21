package vn.edu.iuh.fit.healthcheck.jwt.impl;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.healthcheck.jwt.JwtConfig;
import vn.edu.iuh.fit.healthcheck.jwt.JwtService;


import java.security.Key;
import java.util.function.Function;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    @Autowired
    private JwtConfig jwtConfig;

    private Claims claims = null;

    @Override
    public Claims extractClaims(String token) {
        this.claims = Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    @Override
    public Key getKey() {
        byte[] key = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(key);
    }



    private String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        this.claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Mã token đã hết hạn.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Mã token không được hỗ trợ.");
        } catch (MalformedJwtException e) {
            throw new JwtException("Mã token có định dạng 3 không hợp lệ.");
        } catch (SignatureException e) {
            throw new JwtException("Mã token có định dạng không hợp lệ.");
        } catch (Exception e) {
            throw new JwtException(e.getLocalizedMessage());
        }
        return claims;
    }

    @Override
    public String getCurrentUser() {
        return (String) claims.get("sub");
    }

    @Override
    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }


}
