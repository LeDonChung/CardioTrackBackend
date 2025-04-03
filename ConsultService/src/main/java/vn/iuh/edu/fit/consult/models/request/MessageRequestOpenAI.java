package vn.iuh.edu.fit.consult.models.request;

import lombok.Builder;

@Builder
public class MessageRequestOpenAI {
    String content;
    String role;
}
