package vn.edu.iuh.fit.inventory.services;

import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;

public interface ShelfService {
    PageDTO<ShelfResponse> getPagesSheft(int page, int size, String sortBy, String sortName);
}
