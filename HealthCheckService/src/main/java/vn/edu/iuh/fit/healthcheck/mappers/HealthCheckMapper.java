package vn.edu.iuh.fit.healthcheck.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import vn.edu.iuh.fit.healthcheck.model.dto.request.HealthCheckTestRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.request.QuestionRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.request.UserAnswerRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.request.UserInfoRequest;
import vn.edu.iuh.fit.healthcheck.model.dto.response.HealthCheckTestResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.QuestionResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserAnswerResponse;
import vn.edu.iuh.fit.healthcheck.model.dto.response.UserInfoResponse;
import vn.edu.iuh.fit.healthcheck.model.entity.HealthCheckTest;
import vn.edu.iuh.fit.healthcheck.model.entity.Question;
import vn.edu.iuh.fit.healthcheck.model.entity.UserAnswer;
import vn.edu.iuh.fit.healthcheck.model.entity.UserInfo;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HealthCheckMapper {
    // Chuyển từ Entity sang DTO
    HealthCheckTestResponse toResponseDTO(HealthCheckTest healthCheckTest);
    QuestionResponse toResponseDTO(Question question);
    UserAnswerResponse toResponseDTO(UserAnswer userAnswer);
    UserInfoResponse toResponseDTO(UserInfo userInfo);

    // Chuyển từ DTO sang Entity
    HealthCheckTest toEntity(HealthCheckTestRequest healthCheckTestRequest);
    Question toEntity(QuestionRequest questionRequest);
    UserAnswer toEntity(UserAnswerRequest userAnswerRequest);
    UserInfo toEntity(UserInfoRequest userInfoRequest);
}