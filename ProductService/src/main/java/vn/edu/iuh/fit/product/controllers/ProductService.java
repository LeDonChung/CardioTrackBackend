package vn.edu.iuh.fit.product.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductService {
    @GetMapping("/home")
    public String home() {
        return "Product Service";
    }
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('READ_A')")
    public String user() {
        return "Product Service USER";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "Product Service ADMIN";
    }
}
