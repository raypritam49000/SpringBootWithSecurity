package com.user.management.system.service.impl;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.user.management.system.dto.AuthTokenDTO;
import com.user.management.system.dto.AuthTokenDetailsDTO;
import com.user.management.system.exception.AlreadyExistsException;
import com.user.management.system.exception.ResourceNotFoundException;
import com.user.management.system.jwt.request.JwtRequest;
import com.user.management.system.security.JsonWebTokenUtility;
import com.user.management.system.utility.CvsFileWriter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.management.system.entity.User;
import com.user.management.system.dto.UserDTO;
import com.user.management.system.repository.UserRepository;
import com.user.management.system.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmailId(userDTO.getEmailId());

        if(userOptional.isPresent()){
           throw new AlreadyExistsException("User already exists !!!");
        }

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found with : " + id));
        user.setId(userDTO.getId());
        user.setEmailId(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmailId(userDTO.getEmailId());
        User updateUser = userRepository.save(user);
        return modelMapper.map(updateUser, UserDTO.class);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(String id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found with : " + id)), UserDTO.class);
    }

    @Override
    public Boolean deleteUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found with : " + id));
        userRepository.delete(user);
        return true;
    }

    @Override
    public AuthTokenDTO authenticateUser(JwtRequest jwtRequest) {

        User user = userRepository.findByEmailId(jwtRequest.getEmailId()).orElseThrow(()->new ResourceNotFoundException("User Not Found with "+jwtRequest.getEmailId()));

        AuthTokenDetailsDTO authTokenDetailsDTO = new  AuthTokenDetailsDTO();
        authTokenDetailsDTO.setFirstName(user.getFirstName());
        authTokenDetailsDTO.setLastName(user.getLastName());
        authTokenDetailsDTO.setId(user.getId());
        authTokenDetailsDTO.setEmailId(user.getEmailId());
        authTokenDetailsDTO.expirationDate = JsonWebTokenUtility.buildExpirationDate();

        // Create auth token
        String jwt = JsonWebTokenUtility.createJsonWebToken(authTokenDetailsDTO);
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Failed to create auth token!");
        }

        AuthTokenDTO authToken = new AuthTokenDTO();
        authToken.setToken(jwt);

        return authToken;
    }

    @Override
    public ByteArrayInputStream load() {
        List<UserDTO> userDTOS = userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        ByteArrayInputStream in = CvsFileWriter.userToCSV(userDTOS);
        return in;
    }

}
