package vn.iuh.edu.fit.consult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsultServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultServiceApplication.class, args);
    }

}
