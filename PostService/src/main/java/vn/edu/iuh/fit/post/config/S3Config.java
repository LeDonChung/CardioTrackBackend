package vn.edu.iuh.fit.post.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${AWS_ACCESS_KEY_ID_POST}")
    private String accessKey;
    @Value("${AWS_SECRET_KEY_POST}")
    private String secretKey;

    @Bean
    public S3Client s3Client() {
        System.out.println(accessKey);
        System.out.println(secretKey);
        return S3Client.builder()
                .region(Region.of("ap-southeast-1"))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

}
