package vn.edu.iuh.fit.post.services.impl;

import com.google.common.io.Files;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import vn.edu.iuh.fit.post.services.S3Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


@Service
public class S3ServiceImpl implements S3Service {

//    @Value("${AWS_ACCESS_KEY_ID}")
//    @Getter
//    private String accessKey;
//
//
//    @Value("${AWS_SECRET_KEY}")
//    @Getter
//    private String secretKey;
//
//
//    @Value("${AWS_S3_BUCKET_NAME}")
//    @Getter
//    private String bucketName;
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;



    private final S3Client s3Client;
    private final Region region;

    public S3ServiceImpl() {
        Dotenv dotenv = Dotenv.load();
        this.accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        this.secretKey = dotenv.get("AWS_SECRET_KEY");
        this.bucketName = dotenv.get("AWS_S3_BUCKET_NAME");
        // In ra thông tin cấu hình chỉ khi cần thiết, tránh việc log thông tin nhạy cảm
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        // Cấu hình S3Client
        this.region = Region.of("ap-southeast-1"); // Region của bạn
        this.s3Client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String contentType = file.getContentType();

        // Tạo yêu cầu upload lên S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(contentType)
                .build();

        // Upload file lên S3
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        // Trả về URL của file trên S3
        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }

    // Hàm xác định contentType cho các loại file
    private String getContentType(File file) throws IOException {
        String fileExtension = Files.getFileExtension(file.getName()).toLowerCase();
        switch (fileExtension) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            default:
                throw new IOException("Unsupported file type: " + fileExtension);
        }
    }
}
