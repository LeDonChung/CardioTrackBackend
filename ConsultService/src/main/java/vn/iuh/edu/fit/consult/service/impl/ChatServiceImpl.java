package vn.iuh.edu.fit.consult.service.impl;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.iuh.edu.fit.consult.entity.User;
import vn.iuh.edu.fit.consult.models.request.MessageRequest;
import vn.iuh.edu.fit.consult.models.request.MessageRequestOpenAI;
import vn.iuh.edu.fit.consult.models.request.ThreadRunRequest;
import vn.iuh.edu.fit.consult.models.response.MessageResponse;
import vn.iuh.edu.fit.consult.repository.UserRepository;
import vn.iuh.edu.fit.consult.service.ChatService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${chatgpt.threads}")
    private String CHATGPT_THREADS_URL;

    @Value("${chatgpt.assistant.id}")
    private String CHATGPT_ASSISTANT_ID;

    @Value("${chatgpt.open.api.key}")
    private String OPEN_API_KEY;

    private final Gson gson = new Gson();
    @Autowired
    private UserRepository userRepository;


    private HttpHeaders createHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("OpenAI-Beta", "assistants=v2");
        headers.add("Authorization", "Bearer " + OPEN_API_KEY);
        return headers;
    }

    public Map<String, Object> createThread() throws IOException {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                CHATGPT_THREADS_URL,
                HttpMethod.POST,
                new HttpEntity<>(createHeaders()),
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @Async
    public CompletableFuture<Map<String, Object>> runThreadToAssistant(String threadId) throws IOException {
        Map<String, Object> tools = Map.of("type", "file_search");
        ThreadRunRequest threadRunRequest = new ThreadRunRequest(CHATGPT_ASSISTANT_ID, tools);
        String body = gson.toJson(threadRunRequest);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                CHATGPT_THREADS_URL + "/" + threadId + "/runs",
                HttpMethod.POST,
                new HttpEntity<>(body, createHeaders()),
                new ParameterizedTypeReference<>() {
                }
        );
        return CompletableFuture.completedFuture(response.getBody());
    }

    public MessageResponse getMessageLast(String threadId) throws IOException {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                CHATGPT_THREADS_URL + "/" + threadId + "/messages?order=desc&limit=1",
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                new ParameterizedTypeReference<>() {
                }
        );
        Map<String, Object> body = response.getBody();
        if (body != null) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) body.get("data");
            if (dataList != null && !dataList.isEmpty()) {
                Map<String, Object> firstData = dataList.get(0);
                String role = (String) firstData.get("role");
                List<Map<String, Object>> contentList = (List<Map<String, Object>>) firstData.get("content");
                if (contentList != null && !contentList.isEmpty()) {
                    Map<String, Object> textContent = contentList.get(0);
                    Map<String, Object> text = (Map<String, Object>) textContent.get("text");
                    String value = (String) text.get("value");

                    return MessageResponse.builder().role(role).content(value).build();
                }
            }
        }
        return defaultMessageResponse();
    }

    private MessageResponse defaultMessageResponse() {
        return MessageResponse
                .builder()
                .role("assistant")
                .content("Anh/chị vui lòng đợi em ít phút, em sẽ phản hồi ngay. Em cảm ơn anh/chị.")
                .build();
    }

    public Map<String, Object> retrieveRunForThread(String threadId, String runId) throws IOException {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                CHATGPT_THREADS_URL + "/" + threadId + "/runs/" + runId,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @Override
    public MessageResponse sendMessage(Long userId, MessageRequest message) {
        try {

            User user = userRepository.findById(userId).orElse(new User());

            if(user.getId() == null) {
                user.setId(userId);
                user = userRepository.save(user);
            }

            String threadId = user.getThread();
            if (threadId == null) {
                threadId = createNewThreadForUser(user);
            }

            sendUserMessage(threadId, message);
            String runId = runThreadToAssistant(threadId).get(10, TimeUnit.SECONDS).get("id").toString();

            return awaitRunCompletion(threadId, runId);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultMessageResponse();
        }
    }

    private String createNewThreadForUser(User user) throws IOException {
        Map<String, Object> threadResponse = createThread();
        String threadId = (String) threadResponse.get("id");
        if (threadId == null) throw new RuntimeException("Cannot create thread");
        user.setThread(threadId);
        userRepository.save(user);
        return threadId;
    }

    private void sendUserMessage(String threadId, MessageRequest message) throws IOException {
        MessageRequestOpenAI messageRequest = MessageRequestOpenAI
                .builder()
                .role("user")
                .content(message.getMessage())
                .build();
        String body = gson.toJson(messageRequest);
        restTemplate.exchange(
                CHATGPT_THREADS_URL + "/" + threadId + "/messages",
                HttpMethod.POST,
                new HttpEntity<>(body, createHeaders()),
                new ParameterizedTypeReference<>() {
                }
        );
    }

    private MessageResponse awaitRunCompletion(String threadId, String runId) throws InterruptedException, IOException {
        int loopCount = 0;
        while (loopCount < 30) {
            Map<String, Object> runStatus = retrieveRunForThread(threadId, runId);
            if ("completed".equals(runStatus.get("status"))) {
                return getMessageLast(threadId);
            }
            loopCount++;
            Thread.sleep(1000);
        }
        return defaultMessageResponse();
    }

    @Override
    public List<MessageResponse> getMessages(Long userId) throws IOException {
        User user = userRepository.findById(userId).orElse(new User());

        if(user.getId() == null) {
            user.setId(userId);
            user = userRepository.save(user);
        }
        String threadId = user.getThread();
        if (threadId == null) {
            threadId = createNewThreadForUser(user);
        }
        return getMessages(threadId);
    }

    @Override
    public boolean createUser(Long userId) {
        User user = new User();
        user.setId(userId);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean isUserExist(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent();
    }

    public List<MessageResponse> getMessages(String threadId) throws IOException {
        List<MessageResponse> responses = new ArrayList<>();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                CHATGPT_THREADS_URL + "/" + threadId + "/messages?order=asc&limit=100",
                HttpMethod.GET,
                new HttpEntity<>(createHeaders()),
                new ParameterizedTypeReference<>() {
                }
        );
        Map<String, Object> body = response.getBody();
        if (body != null) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) body.get("data");
            if (dataList != null && !dataList.isEmpty()) {
                dataList.forEach(firstData -> {
                    String role = (String) firstData.get("role");
                    List<Map<String, Object>> contentList = (List<Map<String, Object>>) firstData.get("content");
                    if (contentList != null && !contentList.isEmpty()) {
                        Map<String, Object> textContent = contentList.get(0);
                        Map<String, Object> text = (Map<String, Object>) textContent.get("text");
                        String value = (String) text.get("value");
                        responses.add(MessageResponse.builder().role(role).content(value).build());
                    }
                });
            }
        }
        return responses;
    }
}
