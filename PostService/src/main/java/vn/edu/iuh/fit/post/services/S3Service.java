package vn.edu.iuh.fit.post.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface S3Service {
    String uploadFile(MultipartFile file)throws IOException;
}
