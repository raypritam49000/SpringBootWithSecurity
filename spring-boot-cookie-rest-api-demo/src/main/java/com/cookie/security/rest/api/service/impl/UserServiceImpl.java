package com.cookie.security.rest.api.service.impl;

import com.cookie.security.rest.api.dto.UserDTO;
import com.cookie.security.rest.api.entity.User;
import com.cookie.security.rest.api.exceptions.ResourceNotFoundException;
import com.cookie.security.rest.api.repository.UserRepository;
import com.cookie.security.rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found with username: " + username));
        return UserDTO.builder().id(user.getId()).email(user.getEmail()).password(user.getPassword()).username(user.getUsername()).build();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = User.builder().email(userDTO.getEmail()).username(userDTO.getUsername()).password(userDTO.getPassword()).build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createUser = userRepository.save(user);
        return UserDTO.builder().id(createUser.getId()).email(createUser.getEmail()).password(createUser.getPassword()).username(createUser.getUsername()).build();
    }
}
