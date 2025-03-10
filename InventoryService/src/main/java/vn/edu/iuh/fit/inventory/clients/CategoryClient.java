package vn.edu.iuh.fit.inventory.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service", path = "/api/v1/medicine")
public interface CategoryClient {
}
