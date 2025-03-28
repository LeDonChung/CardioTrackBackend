package vn.edu.iuh.fit.student.thanhtuyen.chatservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.UserDto;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.User;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {MessageMapper.class})
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Chuyển từ Entity -> DTO
    UserDto toDTO(User user);

    // Chuyển từ DTO -> Entity
    User toEntity(UserDto userDTO);

    // Chuyển đổi danh sách
    List<UserDto> toDTOList(List<User> users);
}

