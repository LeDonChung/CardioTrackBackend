package vn.edu.iuh.fit.gateway.filter;

import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.logging.LoggingRebinder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RateLimitingFilter implements GatewayFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 4;
    private static final String CACHE_NAME = "rateLimitCache";  // Tên cache

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private LoggingRebinder loggingRebinder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Lấy IP của client
        String clientIp = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();

        // Kiểm tra cache để xem số yêu cầu từ client này
        Cache cache = cacheManager.getCache(CACHE_NAME);
        if (cache != null) {

            Integer requestCount = cache.get(clientIp, Integer.class);

            // Nếu số yêu cầu vượt quá giới hạn, trả về mã lỗi 429
            if (requestCount != null && requestCount >= MAX_REQUESTS_PER_MINUTE) {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }

            cache.put(clientIp, (requestCount == null ? 1 : requestCount + 1));  // Cập nhật lại TTL sau mỗi yêu cầu
        }

        return chain.filter(exchange);
    }
}