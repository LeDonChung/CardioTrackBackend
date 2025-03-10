package vn.edu.iuh.fit.post.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import vn.edu.iuh.fit.post.model.dto.request.CommentRequest;
import vn.edu.iuh.fit.post.model.dto.request.PostRequest;
import vn.edu.iuh.fit.post.model.dto.response.CommentResponse;
import vn.edu.iuh.fit.post.model.dto.response.PostResponse;
import vn.edu.iuh.fit.post.model.entity.Comment;
import vn.edu.iuh.fit.post.model.entity.Post;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    Comment toEntity(CommentRequest request); // Chuyển CommentRequest -> Comment Entity

    CommentResponse toResponse(Comment post); // Chuyển Comment Entity -> CommentResponse DTO
}
