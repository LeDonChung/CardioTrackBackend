package vn.edu.iuh.fit.inventory.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.inventory.models.dtos.PageDTO;
import vn.edu.iuh.fit.inventory.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.inventory.models.dtos.responses.ShelfResponse;
import vn.edu.iuh.fit.inventory.services.ShelfService;

@RestController
@RequestMapping("/api/v1/shelf")
public class ShelfController {
    @Autowired
    private ShelfService shelfService;

    // Get all shelf
    @GetMapping
    public ResponseEntity<BaseResponse<PageDTO<ShelfResponse>>> getPages(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam(required = false) String sortBy,
                                                                         @RequestParam(required = false) String sortName) {
        PageDTO<ShelfResponse> pageDTO = shelfService.getPagesSheft(page, size, sortBy, sortName);
        return ResponseEntity.ok(
                BaseResponse
                        .<PageDTO<ShelfResponse>>builder()
                        .data(pageDTO)
                        .success(true)
                        .build()
        );
    }
}
