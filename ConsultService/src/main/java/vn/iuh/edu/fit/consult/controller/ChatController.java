package vn.iuh.edu.fit.consult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import vn.iuh.edu.fit.consult.models.request.MessageRequest;
import vn.iuh.edu.fit.consult.models.response.MessageResponse;
import vn.iuh.edu.fit.consult.service.ChatService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/consult")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/{userId}")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ResponseEntity<MessageResponse> sendMessage(
            @RequestBody MessageRequest messageRequest,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                chatService.sendMessage(userId, messageRequest)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<MessageResponse>> getMessages(
            @PathVariable Long userId
    ) throws IOException {
        return ResponseEntity.ok(
                chatService.getMessages(userId)
        );
    }
}
