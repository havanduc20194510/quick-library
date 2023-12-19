package com.haduc.quicklibbooksmanagement.service.impl;

import com.haduc.quicklibbooksmanagement.dto.UserDto;
import com.haduc.quicklibbooksmanagement.entity.User;
import com.haduc.quicklibbooksmanagement.mapper.UserMapper;
import com.haduc.quicklibbooksmanagement.repository.UserRepository;
import com.haduc.quicklibbooksmanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        Date currentDate = new Date();
        userDto.setCreatedAt(currentDate);
        userDto.setUpdatedAt(currentDate);
        User user = userMapper.toUser(userDto);
        User userSaved = userRepository.save(user);
        return userMapper.toUserDto(userSaved);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElse(null);
        if (user != null) {
            Date currentDate = new Date();
            userDto.setUpdatedAt(currentDate);
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            user.setRole(userDto.getRole());
            user.setUpdatedAt(userDto.getUpdatedAt());
            User userUpdated = userRepository.save(user);
            return userMapper.toUserDto(userUpdated);
        }
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDto).collect(Collectors.toList());
    }
}
