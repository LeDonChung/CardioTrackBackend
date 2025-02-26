package vn.edu.iuh.fit.notification.services;

import java.util.Map;

public interface ThymeleafService {
    String createContent(String template, Map<String, Object> variables);
}
