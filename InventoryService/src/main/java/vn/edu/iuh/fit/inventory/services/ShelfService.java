package vn.edu.iuh.fit.inventory.services;

import org.springframework.security.core.parameters.P;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.requests.ShelfRequest;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

import java.util.List;

public interface ShelfService {
    PageDTO<ShelfResponse> getPagesSheft(int page, int size, String sortBy, String sortName);

    ShelfResponse save(ShelfRequest request);

    ShelfResponse getShelfById(Long id);

    void deleteShelfById(Long id);

    PageDTO<ShelfResponse> findShelfByStatus(String status, int page, int size, String sortBy, String sortName);
}
