package vn.edu.iuh.fit.auth.exceptions;

public class JwtException extends RuntimeException{
    public JwtException(String message) {
        super(message);
    }
}
