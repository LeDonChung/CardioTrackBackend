package vn.edu.iuh.fit.post.jwt;

public interface JwtService {
    //lấy token từ user khi đăng nhập
    String extractUsername(String token); // Lấy username từ JWT Token
}
