package vn.iuh.edu.fit.consult.service;

import org.springframework.web.multipart.MultipartFile;
import vn.iuh.edu.fit.consult.models.request.MessageRequest;
import vn.iuh.edu.fit.consult.models.response.MedicineResponse;
import vn.iuh.edu.fit.consult.models.response.MessageResponse;
import vn.iuh.edu.fit.consult.models.response.SearchDataResponse;

import java.io.IOException;
import java.util.List;

public interface ChatService {
    MessageResponse sendMessage(Long userId, MessageRequest message);
    List<MessageResponse> getMessages(Long userId) throws IOException;
    boolean createUser(Long userId);
    boolean isUserExist(Long userId);
    List<MedicineResponse> searchData(String file) throws IOException;
}
