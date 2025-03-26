package vn.edu.iuh.fit.healthcheck.model.dto.request;

import java.util.List;

public class GPTRequest {
    private String model;
    private List<Message> messages;
    private int max_tokens;
    private double temperature;

    // Constructor
    public GPTRequest(String model, String prompt, int max_tokens, double temperature) {
        this.model = model;  // Đảm bảo model là "gpt-4o-mini"
        this.messages = List.of(new Message("user", prompt)); // Chúng ta sẽ sử dụng kiểu "user" cho tin nhắn đầu tiên
        this.max_tokens = max_tokens;
        this.temperature = temperature;
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // Class Message to store user message
    static class Message {
        private String role;  // "user" or "assistant"
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Getters and Setters
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}