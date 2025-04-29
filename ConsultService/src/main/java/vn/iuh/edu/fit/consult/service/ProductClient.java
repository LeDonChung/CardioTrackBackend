package vn.iuh.edu.fit.consult.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.iuh.edu.fit.consult.models.response.BaseResponse;
import vn.iuh.edu.fit.consult.models.response.MedicineResponse;

@FeignClient(name = "product-service", path = "/api/v1/medicine")
public interface ProductClient {
    @GetMapping("/get-by-sku")
    public ResponseEntity<BaseResponse<MedicineResponse>> findBySku(@RequestParam String sku);
}
