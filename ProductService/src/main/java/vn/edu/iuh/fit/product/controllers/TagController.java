package vn.edu.iuh.fit.product.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.product.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.TagByObjectResponse;
import vn.edu.iuh.fit.product.services.TagService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

    @Autowired
    private TagService tagService;
    @GetMapping("/get-object")
    public ResponseEntity<BaseResponse<Set<TagByObjectResponse>>> getObject() {
        return new ResponseEntity<>(
                BaseResponse.<Set<TagByObjectResponse>>builder()
                        .data(tagService.getObject())
                        .success(true)
                        .code("200")
                        .build(),
                org.springframework.http.HttpStatus.OK
        );
    }
}
