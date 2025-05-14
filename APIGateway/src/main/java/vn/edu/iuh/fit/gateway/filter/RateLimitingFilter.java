package vn.edu.iuh.fit.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class RateLimitingFilter implements GatewayFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 4;
    private static final String GLOBAL_KEY = "global_rate_limit";

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return redisTemplate.opsForValue()
                .increment(GLOBAL_KEY)
                .flatMap(count -> {
                    // Lần đầu tạo key trong chu kỳ, set TTL = 60s
                    if (count == 1) {
                        redisTemplate.expire(GLOBAL_KEY, Duration.ofMinutes(1)).subscribe();
                    }

                    if (count > MAX_REQUESTS_PER_MINUTE) {
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

                        String body = "{"
                                + "\"error\":\"TOO_MANY_REQUESTS\","
                                + "\"message\":\"Server hiện đang quá tải vui lòng thử lại sau.\""
                                + "}";

                        DataBuffer buffer = response.bufferFactory()
                                .wrap(body.getBytes(StandardCharsets.UTF_8));
                        return response.writeWith(Mono.just(buffer));
                    }

                    // Ngược lại cho chạy tiếp
                    return chain.filter(exchange);
                });
    }
}