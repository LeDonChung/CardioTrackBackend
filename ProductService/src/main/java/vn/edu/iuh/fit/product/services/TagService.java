package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.models.dtos.responses.TagByObjectResponse;

import java.util.Set;

public interface TagService {
    Set<TagByObjectResponse> getObject();
}
