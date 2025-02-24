package vn.edu.iuh.fit.post.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    @Value("${jwt.secret:3979244226452948404D6251655468576D5A7134743777217A25432A462D4A61}")
    private String secret;
}
