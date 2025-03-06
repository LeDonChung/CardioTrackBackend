package vn.edu.iuh.fit.pay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import vn.payos.PayOS;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PayServiceApplication {

    @Value("${PAYOS_CLIENT_ID}")
    private String clientId;

    @Value("${PAYOS_API_KEY}")
    private String apiKey;

    @Value("${PAYOS_CHECKSUM_KEY}")
    private String checksumKey;

    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }

    public static void main(String[] args) {
        SpringApplication.run(PayServiceApplication.class, args);
    }

}
