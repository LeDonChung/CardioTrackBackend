package vn.iuh.edu.fit.consult.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
