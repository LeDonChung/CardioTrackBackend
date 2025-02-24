package vn.edu.iuh.fit.post.mappers;

import org.mapstruct.*;
import vn.edu.iuh.fit.post.model.dto.reponse.PostResponse;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.model.entity.Post;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostMapper {
    Post toEntity(PostRequest request); // Chuyển PostRequest -> Post Entity

    PostResponse toResponse(Post post); // Chuyển Post Entity -> PostResponse DTO
}