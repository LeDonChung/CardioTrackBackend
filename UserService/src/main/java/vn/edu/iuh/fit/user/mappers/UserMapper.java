package vn.edu.iuh.fit.user.mappers;


import org.mapstruct.*;
import vn.edu.iuh.fit.user.model.dto.request.UserRegisterRequest;
import vn.edu.iuh.fit.user.model.dto.response.UserResponse;
import vn.edu.iuh.fit.user.model.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(UserRegisterRequest request);

    UserResponse toUserResponse(User user);
}
