package vn.edu.iuh.fit.user.exceptions;

public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
