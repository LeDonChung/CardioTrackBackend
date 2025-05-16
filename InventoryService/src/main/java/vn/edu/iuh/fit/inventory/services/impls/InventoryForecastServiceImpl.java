package vn.edu.iuh.fit.inventory.services.impls;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import vn.edu.iuh.fit.inventory.services.InventoryForecastService;

@Service
public class InventoryForecastServiceImpl implements InventoryForecastService {

    @Override
    public String getForecast(int currentInventory) {
        String url = "http://localhost:5000/api/forecast";
        RestTemplate restTemplate = new RestTemplate();

        // Tạo JSON body
        JSONObject requestBody = new JSONObject();
        requestBody.put("current_inventory", currentInventory);

        // Cấu hình headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo request
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        // Gửi POST request đến Flask
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}

