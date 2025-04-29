package vn.edu.iuh.fit.student.thanhtuyen.chatservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.mappers.MessageMapper;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.mappers.UserMapper;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.MessageRequest;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.MessageDto;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.Message;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.User;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.repositories.MessageRepository;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.repositories.UserRepository;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.services.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MessageDto saveMessage(MessageRequest messageDto) {
        Message message = Message.builder()
                .senderId(messageDto.getSender().getId())
                .receiverId(messageDto.getReceiver().getId())
                .timestamp(messageDto.getTimestamp())
                .build();
        if(messageDto.getContent() != null) {
            message.setContent(messageDto.getContent());
        }
        if(messageDto.getImageUrl() != null) {
            message.setImageUrl(messageDto.getImageUrl());
        }
        User user;
        if (messageDto.getSender().getId() != 0) {
            User consult = userRepository.findById(0L).orElse(null);
            if (consult == null) {
                consult = User.builder()
                        .id(0L)
                        .username("Tư vấn viên")
                        .role("consult")
                        .build();
            }
            user = userRepository.findById(messageDto.getSender().getId()).orElse(null);
            if (user == null) {
                user = User.builder()
                        .id(messageDto.getSender().getId())
                        .role("user")
                        .build();
            }
            user.setUsername(messageDto.getSender().getUsername());
            userRepository.save(user);
        }else {
            user = userRepository.findById(messageDto.getReceiver().getId()).orElse(null);
            if (user == null) {
                user.setRole("user");
                userRepository.save(user);
            }
        }
        message = messageRepository.save(message);
        return messageMapper.toDTO(message);
    }
}
