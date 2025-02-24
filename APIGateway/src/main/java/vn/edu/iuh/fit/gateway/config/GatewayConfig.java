package vn.edu.iuh.fit.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;
import vn.edu.iuh.fit.gateway.filter.JwtAuthenticationFilter;

import java.util.List;

/**
 * Configuration class named {@link GatewayConfig} for setting up API Gateway routes.
 */
@Configuration
public class GatewayConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    // Define the list of public endpoints
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/login"
    );

    /**
     * Configures the route locator to define the routing rules for the gateway.
     *
     * @param builder The RouteLocatorBuilder used to build the RouteLocator.
     * @return A RouteLocator with the defined routes.
     */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://auth-service"))
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://user-service"))
                .route("product-service", r -> r.path("/api/v1/product/**")
                        .uri("lb://product-service"))
                .route("post-service", r -> r.path("/api/v1/posts/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://post-service"))
                .route("notification-service", r -> r.path("/api/v1/notification/**")
                        .uri("lb://notification-service"))
                .build();
    }

}