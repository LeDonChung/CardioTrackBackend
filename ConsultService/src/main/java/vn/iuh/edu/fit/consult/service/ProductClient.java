package vn.iuh.edu.fit.consult.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.iuh.edu.fit.consult.models.response.BaseResponse;
import vn.iuh.edu.fit.consult.models.response.MedicineResponse;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/v1/medicine/get-by-sku")
    BaseResponse<MedicineResponse> getMedicineBySku(@RequestParam("sku") String sku);
}
