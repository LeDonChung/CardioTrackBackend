package vn.edu.iuh.fit.student.thanhtuyen.chatservice.services;

import lombok.Getter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class WebSocketUserService {
    // Get the list of online users
    @Getter
    private final Set<String> onlineUsers = Collections.synchronizedSet(new HashSet<>());
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketUserService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Add a user to the online users list and notify clients
    public void addUser(WebSocketSession session, String username) {
        onlineUsers.add(username);
        sendOnlineUsersUpdate();
    }

    // Remove a user from the online users list and notify clients
    public void removeUser(WebSocketSession session, String username) {
        onlineUsers.remove(username);
        sendOnlineUsersUpdate();
    }

    // Send the list of online users to all connected clients
    private void sendOnlineUsersUpdate() {
        messagingTemplate.convertAndSend("/topic/online-users", onlineUsers);
    }
}
