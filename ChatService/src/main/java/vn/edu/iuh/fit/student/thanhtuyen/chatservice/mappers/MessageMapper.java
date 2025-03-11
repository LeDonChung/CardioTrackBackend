package vn.edu.iuh.fit.student.thanhtuyen.chatservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.MessageDto;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.Message;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    // Chuyển từ Entity -> DTO
    MessageDto toDTO(Message message);

    // Chuyển từ DTO -> Entity
    Message toEntity(MessageDto messageDTO);

    // Chuyển đổi danh sách
    List<MessageDto> toDTOList(List<Message> messages);
}

