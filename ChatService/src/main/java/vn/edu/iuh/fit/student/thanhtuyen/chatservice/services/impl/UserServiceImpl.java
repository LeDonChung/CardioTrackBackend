package vn.edu.iuh.fit.student.thanhtuyen.chatservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.mappers.MessageMapper;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.mappers.UserMapper;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.MessageDto;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.dtos.UserDto;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.Message;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.models.entities.User;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.repositories.MessageRepository;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.repositories.UserRepository;
import vn.edu.iuh.fit.student.thanhtuyen.chatservice.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAllUser();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            //get all messages of user
            List<Message> messages = messageRepository.findByUserId(user.getId());
            UserDto userDto = userMapper.toDTO(user);
            //map messages to messageDtos
            List<MessageDto> messageDtos = new ArrayList<>();
            for (Message message : messages) {
                messageDtos.add(messageMapper.toDTO(message));
            }
            //set messages to userDto
            userDto.setMessages(messageDtos);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            List<Message> messages = messageRepository.findByUserId(user.getId());
            UserDto userDto = userMapper.toDTO(user);
            List<MessageDto> messageDtos = new ArrayList<>();
            for (Message message : messages) {
                messageDtos.add(messageMapper.toDTO(message));
            }
            userDto.setMessages(messageDtos);
            return userDto;
        }
        return null;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
