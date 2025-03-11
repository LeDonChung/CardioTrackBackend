package vn.edu.iuh.fit.student.thanhtuyen.chatservice.services;

import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.MessageRequest;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto saveMessage(MessageRequest messageDto);
}
