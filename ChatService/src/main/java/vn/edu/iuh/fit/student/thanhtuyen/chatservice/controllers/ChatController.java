package vn.edu.iuh.fit.student.thanhtuyen.chatservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.MessageRequest;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.MessageDto;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.services.MessageService;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
public class ChatController {
    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageDto sendMessage(@Payload MessageRequest message) {
        message.setTimestamp(LocalDateTime.now());
        System.out.println("Received message: " + message);
        MessageDto messageDto =  messageService.saveMessage(message);
        return messageDto;
    }
}

