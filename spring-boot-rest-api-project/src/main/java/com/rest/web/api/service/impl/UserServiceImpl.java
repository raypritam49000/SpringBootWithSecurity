package com.rest.web.api.service.impl;

import com.rest.web.api.dto.UserDto;
import com.rest.web.api.entities.User;
import com.rest.web.api.exceptions.ResourceNotFoundException;
import com.rest.web.api.repository.UserRepository;
import com.rest.web.api.service.IUserService;
import com.rest.web.api.utility.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDTO) {
        User user = User.builder().email(userDTO.getEmail()).username(userDTO.getUsername()).password(userDTO.getPassword()).city(userDTO.getCity()).isAdmin(userDTO.getIsAdmin()).build();
        user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
        User createUser = userRepository.save(user);
        return UserDto.builder().id(createUser.getId()).email(createUser.getEmail()).password(createUser.getPassword()).username(createUser.getUsername()).isAdmin(createUser.getIsAdmin()).city(createUser.getCity()).build();
    }

    @Override
    public Optional<UserDto> findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found with : " + username));
        UserDto userDto =  UserDto.builder().id(user.getId()).email(user.getEmail()).password(user.getPassword()).username(user.getUsername()).isAdmin(user.getIsAdmin()).city(user.getCity()).build();
        return Optional.of(userDto);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
