package vn.edu.iuh.fit.healthcheck.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.healthcheck.model.dto.request.HealthCheckTestRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.HealthCheckTestResponse;
import vn.edu.iuh.fit.healthcheck.services.HealthCheckTestService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/healthcheck")
public class HealthCheckController {

    @Autowired
    private HealthCheckTestService healthCheckTestService;

    @PostMapping("/create")
    public ResponseEntity<HealthCheckTestResponse> createHealthCheckTest(@RequestBody HealthCheckTestRequest request) {
        HealthCheckTestResponse response = healthCheckTestService.createHealthCheckTest(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HealthCheckTestResponse>> getAllHealthCheckTests() {
        List<HealthCheckTestResponse> tests = healthCheckTestService.getAllHealthCheckTests();
        return ResponseEntity.ok(tests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthCheckTestResponse> getHealthCheckTestById(@PathVariable Long id) {
        HealthCheckTestResponse test = healthCheckTestService.getHealthCheckTestById(id);
        return ResponseEntity.ok(test);
    }
}