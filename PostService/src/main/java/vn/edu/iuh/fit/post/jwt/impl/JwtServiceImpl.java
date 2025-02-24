package vn.edu.iuh.fit.post.jwt.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.post.jwt.JwtConfig;
import vn.edu.iuh.fit.post.jwt.JwtService;

import java.security.Key;

@Slf4j
@Service

public class JwtServiceImpl  implements JwtService {

   private final JwtConfig jwtConfig;

   @Autowired
    public JwtServiceImpl(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }






    // 1️⃣ Trích xuất username từ JWT Token
    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);  // Lấy userId từ claims
    }

    // 2️⃣ Giải mã toàn bộ Token để lấy Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 3️⃣ Lấy secret key để giải mã Token
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
